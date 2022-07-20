package com.springoop;

public class Main {

    public static void main(String[] args) {

        버스 버스1 = new 버스();
        버스 버스2 = new 버스();

        System.out.println("버스 1번 번호 : " + 버스1.번호);
        System.out.println("버스 2번 번호 : " + 버스2.번호);

        //1
        버스1.승객_탑승(2);

        //2
        System.out.println("탑승 승객 수 : " + 버스1.현재승객수);
        System.out.println("잔여 승객 수 : " + (버스1.최대승객수 - 버스1.현재승객수));
        System.out.println("요금 확인 : " + (버스1.요금 * 버스1.현재승객수));

        //3
        버스1.상태_변경(-50);

        //4
        System.out.println("주유량 : " + 버스1.주유량);

        //5
        버스1.상태_변경("차고지행");

        //6
        버스1.상태_변경(10);

        //7
        System.out.println("상태 : " + 버스1.상태);
        System.out.println("주유량 : " + 버스1.주유량);

        //8
        버스1.상태_변경("운행");

        //9
        버스1.승객_탑승(45);

        //10 alert

        //11
        버스1.승객_탑승(5);

        //12
        System.out.println("탑승 승객 수 : " + 버스1.현재승객수);
        System.out.println("잔여 승객 수 : " + (버스1.최대승객수 - 버스1.현재승객수));
        System.out.println("요금 확인 : " + (버스1.요금 * 버스1.현재승객수));

        //13
        버스1.상태_변경(-55);

        //14


        //Taxi
        택시 택시1 = new 택시();
        택시 택시2 = new 택시();

        System.out.println("택시 1번 번호 : " + 택시1.번호);
        System.out.println("택시 2번 번호 : " + 택시2.번호);

        //1
        택시1.승객_탑승(2);
        택시1.목적지 = "서울역";
        택시1.목적지까지거리 = 2;

        //2 출력
        System.out.println("택시1 탑승승객수 : "+택시1.현재승객수);
        System.out.println("택시1 잔여승객수 : "+(택시1.최대승객수-택시1.현재승객수));
        System.out.println("택시1 기본 요금 확인 : "+택시1.기본요금);
        System.out.println("택시1 목적지 : "+택시1.목적지);
        System.out.println("택시1 목적지까지거리 : "+택시1.목적지까지거리);
        System.out.println("택시1 지불할 요금 : "+택시1.기본요금 + (택시1.목적지까지거리 - 1) * 택시1.거리당요금);
        System.out.println("택시1 상태 : "+택시1.상태);

        //3
        택시1.상태_변경(-80);

        //4
        택시1.요금_결제();

    }
}
