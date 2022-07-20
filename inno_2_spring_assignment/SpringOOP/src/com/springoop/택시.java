package com.springoop;

import java.util.UUID;

public class 택시 extends 대중교통 {
    UUID 번호 = UUID.randomUUID();
    int 주유량 = 100;
    int 속도;
    int 최대승객수 = 4;
    int 현재승객수;
    String 목적지 = "";
    int 목적지까지거리;
    int 기본요금 = 3000;
    int 누적요금;
    int 거리당요금 = 1000;
    int 기본거리 = 1;
    String 상태 = "일반";

    public void 운행(String 변화상태값) {
        if (주유량 >= 10) {
            상태 = 변화상태값;
        } else {
            System.out.println("주유가 필요하다");
        }
    }

    public void 속도_변경(int 속도변화량) {
        if (주유량 < 10) {
            System.out.println("주유량을 확인해주세요.");
            return;
        }
        속도 += 속도변화량;
    }

    public void 상태_변경(int 주유변화량) {
        주유량 = 주유변화량;
        if (주유량 < 10) System.out.println("주유가 필요하다");
    }

    public void 상태_변경(int 주유변화량, String 변화상태값) {
        주유량 = 주유변화량;
        상태 = 변화상태값;
        if (주유량 <= 0 || 상태 == "차고지행") 운행("차고지행");
        if (주유량 < 10) System.out.println("주유가 필요하다");
    }

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
        누적요금 = 기본요금 + (목적지까지거리 - 1) * 거리당요금;

    }

}

