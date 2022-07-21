package com.springoop;

public class Main {

    public static void main(String[] args) {

        System.out.println("버스 2대 생성");
        버스 버스1 = new 버스(100);
        버스 버스2 = new 버스(150);

        System.out.println("-----------------------");
        System.out.println("버스 1대로 진행\n");
        //1
        System.out.println("1. 버스 승객 2명 탑승");
        버스1.승객_탑승(2);

        //2
        System.out.println("2. 버스 출력");
        버스1.상태_출력("승객");

        //3
        System.out.println("3. 버스 주유량 변경");
        버스1.상태_변경(-50);
        System.out.println();
        
        //4
        System.out.println("4. 버스 출력");
        버스1.주유_출력();

        //5
        System.out.println("5. 버스 상태 변경 - 차고지행");
        버스1.상태_변경("차고지행");
        System.out.println();

        //6
        System.out.println("6. 버스 주유량 변경");
        버스1.상태_변경(10);
        System.out.println();

        //7
        System.out.println("7. 버스 출력");
        버스1.상태_출력("주유");

        //8
        System.out.println("8. 버스 상태 변경");
        버스1.상태_변경("운행");
        System.out.println();

        //9
        System.out.println("9. 버스 승객 45명 추가 시도");
        System.out.println();
        System.out.println("10. alert");
        버스1.승객_탑승(45);
        //10 alert

        //11
        System.out.println("11. 버스 승객 5명 추가 시도");
        버스1.승객_탑승(5);

        //12
        System.out.println("12. 버스 출력");
        버스1.상태_출력("승객");

        //13
        System.out.println("13. 버스 주유량 변경");
        System.out.println("15. 주유 경고");
        버스1.상태_변경(-55);
        System.out.println();

        //14
        System.out.println("14. 버스 출력");
        버스1.상태_출력("주유");
        System.out.println();


        System.out.println("-----------------------");
        //Taxi
        System.out.println("택시 2대 생성\n");
        택시 택시1 = new 택시(1001);
        택시 택시2 = new 택시(1002);

        System.out.println();

        System.out.println("-----------------------");
        System.out.println("택시 1대로 진행\n");
        //1
        System.out.println("1. 승객 2명 탑승, 목적지 : 서울역, 목적지까지 거리 : 2km 설정");
        택시1.승객_탑승(2);
        택시1.목적지 = "서울역";
        택시1.목적지까지거리 = 2;
        System.out.println();

        //2 출력
        System.out.println("2. 택시 출력");
        System.out.println("택시1 탑승승객수 : " + 택시1.현재승객수);
        System.out.println("택시1 잔여승객수 : " + (택시1.최대승객수 - 택시1.현재승객수));
        System.out.println("택시1 기본 요금 확인 : " + 택시1.기본요금);
        System.out.println("택시1 목적지 : " + 택시1.목적지);
        System.out.println("택시1 목적지까지거리 : " + 택시1.목적지까지거리);
        System.out.println("택시1 지불할 요금 : " + (택시1.기본요금 + (택시1.목적지까지거리 - 1) * 택시1.거리당요금));
        System.out.println("택시1 상태 : " + 택시1.상태);
        System.out.println();

        //3
        System.out.println("3. 택시 주유량 변경");
        택시1.상태_변경(-80);
        System.out.println();

        //4
        System.out.println("4. 택시 요금 결제");
        택시1.요금_결제();
        System.out.println();

        //5
        System.out.println("5. 택시 출력");
        System.out.println("택시1 주유량 : " + 택시1.주유량);
        System.out.println("택시1 누적요금 : " + 택시1.누적요금);
        System.out.println();

        //6
        System.out.println("6. 택시 승객 5명 탑승 시도");
        System.out.println();

        //7
        System.out.println("7. 택시 승객 alert");
        택시1.승객_탑승(5);
        System.out.println();

        //8
        System.out.println("8. 승객 3명 탑승, 목적지 : 구로디지털단지역, 목적지까지 거리 : 12km 설정");
        택시1.승객_탑승(3);
        택시1.목적지 = "구로디지털단지역";
        택시1.목적지까지거리 = 12;
        System.out.println();

        //9. 출력
        System.out.println("9. 택시 출력");
        System.out.println("택시1 탑승승객수 : " + 택시1.현재승객수);
        System.out.println("택시1 잔여승객수 : " + (택시1.최대승객수 - 택시1.현재승객수));
        System.out.println("택시1 기본 요금 확인 : " + 택시1.기본요금);
        System.out.println("택시1 목적지 : " + 택시1.목적지);
        System.out.println("택시1 목적지까지거리 : " + 택시1.목적지까지거리);
        System.out.println("택시1 지불할 요금 : " + (택시1.기본요금 + (택시1.목적지까지거리 - 1) * 택시1.거리당요금));
        System.out.println();

        //10. 출력
        System.out.println("10. 택시 주유량 변경");
        System.out.println("택시 주유량 : -20");
        System.out.println("13. 주유 경고");
        택시1.상태_변경(-20);
        System.out.println();

        //11
        System.out.println("11. 택시 요금 결제");
        택시1.요금_결제();
        System.out.println();

        //12
        System.out.println("12. 택시 출력");
        System.out.println("택시1 주유량 : " + 택시1.주유량);
        System.out.println("택시1 상태 : " + 택시1.상태);
        System.out.println("택시1 누적요금 : " + 택시1.누적요금);
        System.out.println();

        //13

    }
}
