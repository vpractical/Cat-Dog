package com.y.store.activity

import android.support.v4.app.Fragment
import com.y.router_annotations.Route
import com.y.store.R
import com.y.store.adapter.MainVpAdapter
import com.y.store.base.BaseActivity
import com.y.store.bean.People
import com.y.store.fragment.PictureFragment
import com.y.util.L
import kotlinx.android.synthetic.main.store_activity_main.*

@Route(path = "store/main")
class StoreActivity : BaseActivity() {

    init {
        testPeople()
        testD()
        testI()
    }

    private lateinit var vpAdapter: MainVpAdapter
    private var list = ArrayList<Fragment>()

    override fun layout(): Int {
        return R.layout.store_activity_main;
    }

    override fun init() {
        list.add(PictureFragment.newInstance(0))
        list.add(PictureFragment.newInstance(1))
        list.add(PictureFragment.newInstance(2))
        list.add(PictureFragment.newInstance(3))
        vpAdapter = MainVpAdapter(mActivity, list)
        vpStoreMain.adapter = vpAdapter
        initListener()
    }

    private fun initListener() {

    }

    fun ddd() {

    }

    /**
     * 测试lambda和一些api
     */
    private fun testPeople() {
        val data = listOf(People("join", 19, 1),
                People("kot", 24, 2),
                People("tool", 29, 2)
        )

        //不使用简写语法
        L.e("年龄最大的:", data.maxBy({ p: People -> p.age })?.nick)
        //kotlin有一种语法约定，如果lambda表达式是函数调用的最后一个实参，它可以放到括号外面。
        L.e("年龄最大的:", data.maxBy() { p: People -> p.age }?.nick)
        //当lambda是函数唯一实参时，还可以去掉（）
        L.e("年龄最大的:", data.maxBy { p: People -> p.age }?.nick)
        //可以根据类型推导特性而移除参数类型
        L.e("年龄最大的:", data.maxBy { p -> p.age }?.nick)
        //使用默认参数名称
        L.e("年龄最大的:", data.maxBy { it.age }?.nick)
        //成员引用
        L.e("年龄最大的:", data.maxBy(People::age)?.nick)

        val list = listOf(1, 2)
        val map = mapOf(0 to 1, 1 to 2)
        var value = map[0]
        //中缀函数,关键字infix
        infix fun Map<Int, Int>.getVal(index: Int) = this[index]
        value = map.getVal(0)
        value = map getVal 0
        //扩展属性
        fun testA() {
            fun testB() {
                testC(age = 107)
                testC("卢克尼", address = "本")
                testC("乌龟", 210)
            }
            testB()
        }
        testA()
    }

    /**
     * 类似php，参数可以有默认值，以及命名参数的使用
     */
    private fun testC(name: String = "卢克", age: Int = 106, address: String = "团") {

        L.e(People(name, age, 0, address).toString())
    }

    /**
     * 高级函数
     */
    private fun testD() {

        val list = listOf(1..6, 2..5, 100..112)
        L.e(list.toString())

        var flatList = list.flatMap { it }
        L.e(flatList.toString())

        flatList.forEach(::println)

        var aa = flatList.reduce { acc, i -> acc + i }
        L.e(aa.toString())


        //测试高级函数1(lambda 闭包)(Lambda语法，最后一行返回值就是闭包的返回值)
        testF(1) {
            L.e("高级函数写法1:$it")
            L.e("高级函数写法1:$it")
            true
        }

        //测试高级函数2(lambda 闭包)(Lambda语法，最后一行返回值就是闭包的返回值)
        testF(2, { it ->
            L.e("高级函数写法2:$it")
            L.e("高级函数写法2:$it")
            true
        })

        //测试高级函数3
        var method: (int: Int) -> Boolean = {
            L.e("高级函数写法3:$it")
            false
        }
        testF(3, method)


        var methodG = testG()
        methodG.invoke(1, 2)
        testG().invoke(1, 2)
    }

    /**
     * 类似java中可变参数 String... args
     */
    private fun testE(vararg str: String) {
        val arr = arrayOf("a", "b")
        testE(*arr)
    }

    /**
     * 高级函数,参数是函数
     */
    private fun testF(int: Int, callback: (Int) -> Boolean) {
        if (callback(int)) {

        }
    }

    /**
     * 高级函数，返回值是函数
     */

    private fun testG(): (Int, Int) -> Int {
        return { i, j -> i + j }
    }


    /**
     * TODO函数
     */
    private fun testH() {
        TODO("---")
    }

    /**
     * 函数引用，php那个？？？
     */
    private fun testI() {
        val country = arrayOf("China", "Japan")
        //1.直接引用
        country.forEach(::println)

        //2.类名引用    Hello类名 String::isNotEmpty  String 类名
        val helloWorld = Hello::world
        country.filter { it.isNotEmpty() }.forEach { println(it) }
        country.filter(String::isNotEmpty).forEach(::println)

        //3.调用者引用方法
        val pdfPrinter = PdfPrinter()
        country.forEach(pdfPrinter::println)
    }

    class PdfPrinter {
        fun println(any: Any) {
            kotlin.io.println(any)
        }
    }

    inner class Hello {
        fun world() {
            L.e("Hello World.")
        }
    }
}
