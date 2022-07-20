package com.springoop;

import java.util.UUID;

public class 버스 extends 대중교통 {
    UUID 번호 = UUID.randomUUID();
    int 주유량 = 100;
    int 속도;
    int 최대승객수 = 30;
    int 현재승객수;
    int 요금 = 1000;
    String 상태 = "운행";

    public void 운행(String 변화상태값) {
        if (변화상태값.equals("차고지행")) {
            현재승객수 = 0;
        }
        this.상태 = 변화상태값;
        System.out.println("상태 : "+상태);
    }

    public void 속도_변경(int 속도변화량) {
        if (주유량 < 10) {
            System.out.println("주유량을 확인해주세요..");
            return;
        }
        속도 += 속도변화량;
    }

    public void 상태_변경(int 주유변화량) {
        주유량 += 주유변화량;
        if (주유량 < 10) {
            System.out.println("주유량 : " + 주유량);
            운행("차고지행");
            System.out.println("주유가 필요하다");
        }
    }

    public void 상태_변경(String 변화상태값) {
        운행(변화상태값);
    }

    public void 승객_탑승(int 탑승승객수) {
        if (!상태.equals("운행")) {
            System.out.println("버스가 운행 중이 아닙니다.");
            return;
        }

        if (최대승객수 >= 현재승객수 + 탑승승객수) {
            System.out.println("탑승하실 수 있습니다.");
            현재승객수 += 탑승승객수;
        } else {
            System.out.println("승객이 너무 많습니다. 다음 버스를 이용해주세요.");
        }
    }

}

