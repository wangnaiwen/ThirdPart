package com.wnw.wnw.thirdparttest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author wnw
 * @date 2017/11/20 16:41
 */
public class RxJavaActivity extends Activity{

    TextView rxjavaTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initView();
    }

    private void initView(){
        rxjavaTv = (TextView)findViewById(R.id.rxjava);
        rxjavaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first();
                //second();
                //third();
                //four();
                //five();
                //six();
                seven();
            }
        });
    }

    //第一种方式
    private void first(){
        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i("RxJava", "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RxJava", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("RxJava", s);
            }
        };

        //创建被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Wrold");
                subscriber.onCompleted();
            }
        });

        //订阅
        observable.subscribe(observer);
    }

    //第二种方式
    private void second(){

        //创建订阅者, 他也是extend了观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("RxJava", "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RxJava", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("RxJava", s);
            }
        };

        //创建被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello2");
                subscriber.onNext("Wrold2");
                subscriber.onCompleted();
            }
        });

        //订阅
        observable.subscribe(subscriber);
    }

    //第3种方式
    private void third(){

        //创建订阅者, 他也是extend了观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("RxJava", "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RxJava", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("RxJava", s);
            }
        };

        //创建被观察者1
        //Observable observable = Observable.just("Hello", "Hello2");

        //创建被观察者2
        /*String s[] = new String[]{"Hello", "Hello3"};
        Observable observable = Observable.from(s);*/

        //创建被观察者3
        ArrayList list = new ArrayList();
        list.add("Hello");
        list.add("Hello333");
        list.add("Hello444");
        Observable observable = Observable.from(list);


        //订阅
        observable.subscribe(subscriber);
    }


    //第4种方式
    private void four(){
        //创建观察者Action
        //onNext 的 Action
        Action1<ArrayList> onNextAction = new Action1<ArrayList>() {
            @Override
            public void call(ArrayList arrayList) {
                int length = arrayList.size();
                for (int i = 0 ; i < length; i++){
                    Log.i("RxJava", arrayList.get(i).toString());
                }
            }
        };

        //这是onError的
        Action1<Throwable> onErrorAction1 = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("RxJava", "onError");
            }
        };

        //这是完成
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                Log.i("RxJava", "finish");
            }
        };
        //创建被观察者
        ArrayList list = new ArrayList();
        list.add("Hello");
        list.add("Hello333");
        list.add("Hello444");

        //在这个subscribe内部，把action转换成了subscriber对象，也就是Obersver对象
        Observable.just(list).subscribe(onNextAction, onErrorAction1, onCompleteAction);
    }

    //第5种方式
    private void five(){
        //创建被观察者
        Student student = new Student();
        student.setId(0);
        student.setName("wnw");

        Student student1 = new Student();
        student1.setId(1);
        student1.setName("wnw1");

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("wnw2");

        //在这个subscribe内部，把action转换成了subscriber对象，也就是Obersver对象
        //map就是一个转换的操作符，可以把一个对象转换成你想要的类型，可以进行多次转化：以下就是：Student - > String -> Integer
        Observable.just(student,student1, student2).map(new Func1<Student, String>() {
            @Override
            public String call(Student student) {
                return student.getName();
            }
        }).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.hashCode();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("rxjava", integer+"");
            }
        });
    }

    //第6种方式: 打印学生的所有课程
    private void six(){
        //创建被观察者
        Student student = new Student();
        student.setId(0);
        student.setName("wnw");
        Course course = new Course();
        course.setcId(0);
        course.setName("english");

        Course course1 = new Course();
        course1.setcId(1);
        course1.setName("math");

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(course1);

        student.setCourses(courseList);
        // flatMap就是一个转换的操作符，可以把一个对象转换成你想要的类型，先把Student中的Course转化成一个被观察者
        //意思就是：Course是一个被观察者，Student里面也是一个被观察者，一层一层的剥开

        Observable.just(student).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return Observable.from(student.getCourses());
            }
        }).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {
                Log.d("rxjava", course.getName());
            }
        });
    }

    private void seven(){
        Observable.just("hello", "hello2")
                .subscribeOn(Schedulers.newThread())   //指定：在新的线程中发起
                .observeOn(Schedulers.io())            //指定：在IO线程中处理
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())  //指定：在主线程中消费
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("rxjava", integer+"");
                    }
                });
    }
}
