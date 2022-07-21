package com.springoop;

public class 택시 extends 대중교통 {
    final int 번호;
    int 주유량;
    int 속도;
    int 최대승객수;
    int 현재승객수;
    String 목적지;
    int 목적지까지거리;
    int 기본요금;
    int 누적요금;
    int 거리당요금;
    int 기본거리;
    String 상태;

    public 택시(int 번호) {
        this.번호 = 번호;
        this.주유량 = 100;
        this.최대승객수 = 4;
        this.목적지 = "";
        this.기본요금 = 3000;
        this.거리당요금 = 1000;
        this.기본거리 = 1;
        운행_시작();
        System.out.println(this.번호 + "번 택시 생성!");
        System.out.println("택시"+번호+"번 번호 : " + 번호);
        System.out.println("택시"+번호+"번 주유량 : " + 주유량);
        System.out.println("택시"+번호+"번 상태 : " + 상태);
    }

    @Override
    public void 운행_시작() {
        if (주유량 >= 10) {
            상태 = "일반";
        } else {
            System.out.println("주유가 필요하다");
        }
    }

    @Override
    public void 속도_변경(int 속도변화량) {
        if (주유량 < 10) {
            System.out.println("주유량을 확인해주세요.");
            return;
        }
        속도 += 속도변화량;
    }

    @Override
    public void 상태_변경(int 주유변화량) {
        주유량 += 주유변화량;
        if (주유량 < 10) System.out.println("주유가 필요하다");
    }

    @Override
    public void 상태_변경(String 변화상태값) {
        this.상태 = 변화상태값;
        if (주유량 < 10) System.out.println("주유가 필요하다");
    }

    @Override
    public void 승객_탑승(int 탑승승객수) {
        if (!상태.equals("일반")) {
            System.out.println("탑승 불가");
            return;
        }
        if (최대승객수 >= 현재승객수 + 탑승승객수) {
            System.out.println("탑승하실 수 있습니다.");
            현재승객수 += 탑승승객수;
        } else {
            System.out.println("승객이 너무 많습니다.");
        }
    }

    public void 요금_결제() {
        누적요금 += 기본요금 + (목적지까지거리 - 기본거리) * 거리당요금;
        현재승객수 = 0;

        System.out.println(기본요금 + (목적지까지거리 - 기본거리) * 거리당요금);
    }

}

