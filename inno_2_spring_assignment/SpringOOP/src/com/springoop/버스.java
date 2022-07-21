package com.springoop;

public class 버스 extends 대중교통 {
    final int 번호;
    int 주유량;
    int 속도;
    int 최대승객수;
    int 현재승객수;
    int 요금;
    String 상태;

    public 버스(int 번호) {
        this.번호 = 번호;
        this.주유량 = 100;
        this.최대승객수 = 30;
        this.요금 = 1000;
        운행_시작();
        System.out.println(this.번호 + "번 버스 생성. 운행 시작!");
    }

    @Override
    void 운행_시작() {
        this.상태 = "운행";
    }

    @Override
    public void 속도_변경(int 속도변화량) {
        if (주유량 < 10) {
            System.out.println("주유량을 확인해주세요..");
            return;
        }
        속도 += 속도변화량;
    }

    @Override
    public void 상태_변경(int 주유변화량) {
        주유량 += 주유변화량;
        if (주유량 < 10) {
            상태_변경("차고지행");
            System.out.println("주유가 필요하다");
        }
    }

    @Override
    public void 상태_변경(String 변화상태값) {
        this.상태 = 변화상태값;
    }

    @Override
    public void 승객_탑승(int 탑승승객수) {
        if (!상태.equals("운행")) {
            System.out.println("버스가 운행 중이 아닙니다.");
            return;
        }
        if (최대승객수 >= 현재승객수 + 탑승승객수) {
            System.out.println(탑승승객수+"명이 탑승하셨습니다.");
            현재승객수 += 탑승승객수;
            System.out.println();
        } else {
            System.out.println("승객이 너무 많습니다. 다음 버스를 이용해주세요.");
            System.out.println();
        }
    }

    public void 상태_출력(String 필요정보) {
        if (필요정보.equals("승객")) {
            System.out.println("탑승 승객 수 : " + 현재승객수);
            System.out.println("잔여 승객 수 : " + (최대승객수 - 현재승객수));
            System.out.println("요금 확인 : " + (요금 * 현재승객수));
            System.out.println();
        }
        if (필요정보.equals("주유")) {
            System.out.println("상태 : " + 상태);
            System.out.println("주유량 : " + 주유량);
            System.out.println();
        }
    }

    public void 주유_출력() {
            System.out.println("주유량 : " + 주유량);
            System.out.println();
    }

}

