package com.springoop;

abstract class 대중교통 {
    int 번호;
    int 주유량 = 100;
    int 속도 = 0;
    int 최대승객수;

    abstract void 운행_시작();

    abstract void 속도_변경(int 속도변화량);

    abstract void 상태_변경(int 주유변화량);
    abstract void 상태_변경(String 변화상태값);

    abstract void 승객_탑승(int 탑승승객수);
}
