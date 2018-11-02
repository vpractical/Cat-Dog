package com.y.router_compiler;

import com.google.auto.service.AutoService;
import com.y.router_annotations.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//注解处理器，是Google开发的，用来生成META-INF/services/javax.annotation.processing.Processor文件
@AutoService(Processor.class)
//指定使用的Java版本
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
//注册给哪些注解的
//@SupportedAnnotationTypes(Constant.ANNOTATIONS_ROUTE)
public class RouteProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    private static final String SUFFIX = "_Router";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //初始化我们需要的基础工具
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //返回所有被注解了@xxx的元素的列表
        Set<? extends Element> routeSet = roundEnvironment.getElementsAnnotatedWith(Route.class);

        for (Element element : routeSet) {
            // 检查被注解为@Factory的元素是否是一个类
            if (element.getKind() != ElementKind.CLASS) {
                error(element, "Only classes can be annotated with @%s", Route.class.getSimpleName());
                return true; // 退出处理
            }

            //解析，并生成代码
            analysisAnnotated(element);
        }


        return false;
    }

    private void analysisAnnotated(Element element) {

        Route route = element.getAnnotation(Route.class);
        String value = route.path();
        String clazzName = element.getSimpleName() + SUFFIX;
        StringBuilder builder = new StringBuilder()
                .append("package com.y.router_compiler.auto;\n\n")
                .append("public class ")
                .append(clazzName)
                .append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");

        // this is appending to the return statement
        builder.append(value).append(clazzName).append(" !\\n");


        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class

        try { // write the file
            JavaFileObject source = mFiler.createSourceFile("com.y.router_compiler.auto." + clazzName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(Route.class.getCanonicalName());
        return set;
    }
}
