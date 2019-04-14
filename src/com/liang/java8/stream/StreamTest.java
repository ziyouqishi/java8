package com.liang.java8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public List<Teacher> getTeachers(){
        List<Teacher> teachers=new LinkedList<>();
        teachers.add(new Teacher(19,"zhangSan","女"));
        teachers.add(new Teacher(45,"liSi","男"));
        teachers.add(new Teacher(20,"wangWU","女"));
        teachers.add(new Teacher(19,"找Liu","男"));
        return  teachers;
    }

    public List<Student> getStudents(){
        List<Student> students=new LinkedList<>();
        students.add(new Student(12,"xiaoMing"));
        students.add(new Student(10,"xiaoHong"));
        students.add(new Student(6,"xiaoHua"));
        return students;
    }

    /**
     * 像filter，map 这样只描述 Stream，最终不产生新集合的方法叫作惰性求值方法；而像 count，collect 这样
     * 最终会从 Stream 产生值的方法叫作及早求值方法
     */

    /**
     * 判断一个操作是惰性求值还是及早求值很简单：只需看它的返回值。如果返回值是 Stream，
     * 那么是惰性求值；如果返回值是另一个值或为空，那么就是及早求值
     */
    /**
     * 方法引用是一种引用方法的轻量级语法，形如：ClassName::methodName。
     */
    public  void test(){
        /**
         * 统计年龄大于10的人数,filter是对stream对象里面的值进行过滤，该函数必须返回true或者false
         */
        long count=getTeachers().stream().filter(teacher -> teacher.getAge()>10).count();
        //lambda表达式后面也可以是一个代码块，类似于filter这种操作，需要在代码块中返回一个布尔值
        long count2=getTeachers().stream().filter(teacher -> {
            teacher.setAge(teacher.getAge()-5);
            return teacher.getAge()>10;}).count();

        //得到性别为女的集合并使用forEach做遍历输出
        List<Teacher> list=getTeachers().stream().filter(teacher -> teacher.getSex().equals("女"))
                .collect(Collectors.toList());
        //forEach，用于遍历集合,下面两行代码是等效的
        list.stream().forEach(teacher -> System.out.println(teacher));
        list.forEach(System.out::println);//传递的是方法的引用，编译环境能够知道需要将什么值作为参数传入到该方法中

        /**
         * map可以将一种类型转化为另一种类型，将一个流中的值转换成一个新的流，下面的代码是将Teacher转换成了Integer
         * map返回的是什么类型，就转换成了什么类型
         */
        List<Integer> list1=getTeachers().stream().map(teacher -> teacher.getAge()+12).collect(Collectors.toList());//返回年龄集合，每个年龄加了12岁
        List<Integer> list3=Stream.of("Hello","World","java","I").map(s -> s.length()).collect(Collectors.toList());//将字符串的长度组合成一个数组

        //使用Stream.of(...)的方式构建一个stream
        List<String> strings= Stream.of("a","b","c").collect(Collectors.toList());
        //转换为set集合
        Set<Integer> integers= Stream.of(23,34,11,66,12).collect(Collectors.toSet());

        /**
         * flatMap 方法可用 Stream 替换值，然后将多个 Stream 连接成一个 Stream
         * flatMap将多个类型的集合,合并成一个集合，但是必须返回Stream类型。
         * Teacher和Student都是Object子类，所以合并后用Object接收
         */
        /**
         * 控制台输出
         * Teacher{age=19, name='zhangSan', sex='女', students=null}
         * Teacher{age=45, name='liSi', sex='男', students=null}
         * Teacher{age=20, name='wangWU', sex='女', students=null}
         * Teacher{age=19, name='找Liu', sex='男', students=null}
         * Student{age=12, name='xiaoMing'}
         * Student{age=10, name='xiaoHong'}
         * Student{age=6, name='xiaoHua'}
         */
        List<Object> list4=Stream.of(getTeachers(),getStudents()).flatMap(objects -> objects.stream()).collect(Collectors.toList());
        System.out.println(list4);

        /**
         * 获取集合中的最大值和最小值
         * 使用get获取操作后的对象或者值
         */
        Student stuAgeMin=getStudents().stream().min(Comparator.comparing(student -> student.getAge())).get();//获取年龄最小的学生
        Student stuAgeMax=getStudents().stream().max(Comparator.comparing(student -> student.getAge())).get();//获取年龄最大的学生

        /**
         * 排序
         */
       List<Teacher> teacherList=getTeachers().stream().sorted(Comparator.comparing(teacher -> teacher.getAge())).collect(Collectors.toList());
        System.out.println(teacherList);//按老师年龄输出，年龄由小到大

       List<Integer> numbers=Stream.of(9,12,5,2,11).sorted().collect(Collectors.toList());
       numbers.forEach(System.err::print);//输出：2 5 9 11 12
        System.out.println(stuAgeMin);
    }

    /**
     * 收集器collect的其他用法
     */
    public void collectionTest(){
        /**
         * 生成一个字符串，该集合最后生成一个具有一定形式的字符串
         * 希望将一个集合转换成这种形式的字符串: {item1.item2,item3...}
         */

        //joining(...),第一个参数表示分隔符，第二个参数表示前缀，第三个参数表示后缀
        String str=Stream.of(12,33,43,67,77,88,93).map(s->s.toString()).collect(Collectors.joining(",","{","}"));
        System.err.println(str);//输出：{12,33,43,67,77,88,93}
        /**
         * 转换成其他的集合类
         *
         * 之前的操作都是将集合转换为List，Set这些抽象接口，Stream类在背后自动为我们选择合适的类型，
         * 我们可以自定义转换的集合类型
         */
        List<Integer> numbers2=Stream.of(9,12,5,2,11).sorted().collect(Collectors.toCollection(LinkedList::new));
        List<String> numbers3=Stream.of(9,12,5,2,11).map(a -> a+"").collect(Collectors.toCollection(Vector::new));
        //转换为Map对象，key和value分别是老师的名字和性别

        Map<String,String> stringMap=getTeachers().stream().collect(Collectors.toMap(teacher->teacher.getName(),teacher -> teacher.getSex()));
        for (Map.Entry<String,String> stringListEntry : stringMap.entrySet()) {
            System.err.println(stringListEntry.getKey()+":"+stringListEntry.getValue());
        }

        /**
         * 转换成值
         */
       Optional<Teacher>t=getTeachers().stream().collect(Collectors.maxBy(Comparator.comparing(teacher -> teacher.getAge())));
       Optional<Teacher>t2=getTeachers().stream().collect(Collectors.minBy(Comparator.comparing(teacher -> teacher.getAge())));
       //计算平均年龄(平均数有可能是小数，所以要用都double来接收)
       double averagingAge= getTeachers().stream().collect(Collectors.averagingInt(teacher->teacher.getAge()));
       System.out.println(averagingAge);

        /**
         * 数据分块
         *
         * 该流操作是将一个集合其分解成两个集合，比如一个Teacher集合，想根据性别分成两个集合，这时就可以使用数据分块了
         *
         * 它使用 Predicate 对象判断一个元素应该属于哪个部分，并根据布尔值返回一
         * 个 Map 到列表。因此，对于 true List 中的元素，Predicate 返回 true；对其他 List 中的
         * 元素，Predicate 返回 false。
         */
        Map<Boolean,List<Teacher>> maps=getTeachers().stream().collect(Collectors.partitioningBy(teacher -> teacher.getSex().equals("女")));
        List<Teacher> t1=maps.get(true);   t1.stream().forEach(teacher -> System.out.println(teacher));//输出所有女老师
        maps.get(false).stream().forEach(teacher -> System.out.println(teacher));//输出所有男老师

        /**
         * 数据分组
         *
         * 数据分组是一种更自然的分割数据操作，与将数据分成 ture 和 false 两部分不同，可以使
         * 用任意值对数据分组,返回的Map对象key是分组的字段
         * 和sql中的group by很像
         */
        Map<String,List<Teacher>>teacherListMap=getTeachers().stream().collect(Collectors.groupingBy(teacher -> teacher.getSex()));
        List<Teacher> t3=teacherListMap.get("女");   t3.stream().forEach(teacher -> System.out.println(teacher));//输出所有女老师
        teacherListMap.get("男").stream().forEach(teacher -> System.out.println(teacher)); //输出所有男老师

        /**
         * 组合收集器
         */
        //根据老师性别分组并且输出个数（非常类似sql中的group by和聚合函数的使用）,使用了counting()
        Map<String,Long> counts=getTeachers().stream().collect(Collectors.groupingBy(teacher -> teacher.getSex(),Collectors.counting()));
        System.err.println("女老师个数为"+counts.get("女"));//输出：女老师个数为2
        System.err.println("男老师个数为"+counts.get("男"));//输出：男老师个数为4

        //按照老师的性别输出老师的姓名，使用了mapping(...)方法，第一参数表示分组之后需要输出什么值，这里是输出name，第二个是以
        //什么集合形式输出，这里是以List集合的方式输出
        Map<String,List<String>> stringListMap=getTeachers().stream().collect(Collectors.groupingBy(teacher -> teacher.getSex(),Collectors.
                mapping(teacher -> teacher.getName(),Collectors.toList())));
        stringListMap.get("男").stream().forEach(System.err::println);//输出所有男老师的姓名
        stringListMap.get("女").stream().forEach(System.err::println);//输出所有女老师的姓名

    }

    public static void main(String[] arr){
        StreamTest  testClass=new StreamTest();
        testClass.test();
        //testClass.collectionTest();
    }
}
