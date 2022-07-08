# AWS

## 1. AWS에 대한 이해와 목표

![github actions와 aws 운영 모형](https://user-images.githubusercontent.com/84966961/169933838-92764141-88fc-41c8-a03b-87c395d240ed.png)

 최종 목표는 위 그림 모형에 따라 프로젝트를 github에 push하여 github actions를 통해 테스트, 빌드, 배포 과정을 거쳐 AWS의 사용법을 배운다.

 - 클라우드 서비스와 on premise 개념을 인지한다.
 - AWS에 프로젝트를 단순 배포해본다.
 - 배포의 간편화와 Paas 방식으로 환경 구축없이 배포를 해본다.
 - CI/CD 개념을 이해하고 github actions을 통해 배포를 해본다.
 - 무중단 배포를 해본다. (로드밸런서, 롤링배포)


<br>

## 2. AWS ubuntu 인스턴스 생성 및 접속

### 2-1 AWS 회원가입

 - 회원가입 후 로그인하고 난 후에 오른쪽 위에 보면 서버의 위치를 서울로 변경해줌.

### 2-2 AWS 인스턴스 생성

 - 운영체제는 `Ubuntu Server 22.04 LTS (HVM), SSD Volume Type`을 사용하였으며, 프리 티어 사용 가능 버전을 사용함.
 - 인스턴스당 무료로 30GB까지 제공되므로 변경함.
 - 키페어가 없을시 RSA 방식으로 발급받아 컴퓨터에 저장해둔다.(참고 공개키 암호시스템)

### 2-3 MobaXterm에서 접속(설치 및 실행)

 - remote computing을 위한 어플리케이션을 다운로드 받고 설치한다..

 다운로드 주소 : https://mobaxterm.mobatek.net/download-home-edition.html

 - 프로그램 실행 후 왼쪽 위 session 클릭(아래 사진은 이미 서버접속 한 상태라 ip가 보임.)

 ![image](https://user-images.githubusercontent.com/84966961/169935443-2f6b56cc-df56-4178-abea-151a5d614a83.png)

 - session 접속에 필요한 정보 입력

![image](https://user-images.githubusercontent.com/84966961/169936039-e2e896ac-e479-4900-ba1f-4ad31f233b44.png)

 ```
 Remote host : AWS의 퍼블릭 IPv4 주소
 Specify userename : ubuntu (ubuntu 접속시 default 값 - 문서 참조)
 Use private key : 개인키 파일 (위에서 저장했던 RSA 방식 pem 파일 설정)
 ```

 - ok 누르면 서버 접속

<br>

 ## 3. IP, Packet, Port 개념 이해

 ### 3-1. 패킷 전송 방식

 1. 서킷 스위칭 방식 : 선이 늘어나야함

 ```
  A ┬─────────── B
    ├─────────── C
    └─────────── D
    .
    .
    .
 ```


 2. 패킷 스위칭 방식 : 비용이 적게듬. 네트워크 활용 극대화(기사 공부 내용)

```
  A ───○────○─── B
  J ───┤    ├─── C
  K ───○────○─── D
       └────┬─── Z
            └─── H
```

 - 중간의 라우터(가운데 `○` 모양)들을 거쳐가며 라우터는 경로를 목적지(B)를 탐색하여 패킷을 보낸다.
 - 가령 B가 A, J, K로부터 데이터(패킷)를 받게 되면 데이터 조각인 패킷으로 나누어져 받게되고 B에서 무작위로 받은 패킷을 헤더에 저장된 정보(출발지, 데이터의 순서 등)를 참고로 재조립하게 된다.


 ### 3-2. IPv4, IPv6

 - 위에서 각 B, C 등의 컴퓨터를 분별할 수 있는 유일한 값의 체계로 IPv4 방식이 존재함.
 - 정수값 0 ~ 42억 9천 개. (000.000.000.000)
 - A, B, C, E 클래스로 나뉘어 사용 중. (참고 : https://www.meridianoutpost.com/resources/articles/IP-classes.php)

 -> 42억 개로 부족하여 실제로는 내부에서 `NAT (Network Address Translation)`를 통해 gateway에서 private ip(상대주소)를 사용함.
 -> 하지만 사물인터넷과 같은 더욱 많고 다양한 기기들을 사용하기 위해 IPv6 출현. (IPv5 테스트용)

 IPv6 : IPv4의 주소 고갈 문제를 해결하기 위하여 기존의 IPv4주소 체계를 128비트 크기로 확장한 차세대 인터넷 프로토콜 주소 (참고 : [한국인터넷정보센터](https://xn--3e0bx5euxnjje69i70af08bea817g.xn--3e0b707e/jsp/resources/ipv6Info.jsp#:~:text=IPv6%EC%A3%BC%EC%86%8C%EB%8A%94%20IPv4%EC%9D%98,%EC%B0%A8%EC%84%B8%EB%8C%80%20%EC%9D%B8%ED%84%B0%EB%84%B7%20%ED%94%84%EB%A1%9C%ED%86%A0%EC%BD%9C%20%EC%A3%BC%EC%86%8C%EC%9E%85%EB%8B%88%EB%8B%A4))

 ### 3-3. Port

 - 패킷이 목적지(IP 주소)로 향하는 과정에서 여러 어플리케이션 간의 분간을 하기 위해 `포트(Port)`를 사용함.
 - ex) 카카오톡, 네이트온 등 각 어플리케이션끼리 혼선이 없도록.
 - Port는 2^16개로 65536개를 사용 가능함.

 - 최종 목적지 정보

 헤더의 정보 : `ip주소:Port번호`

<br>

 ## 4. 방화벽

 - 외부에서 내부로 들어갈 때 인바운드 규칙에 의해 모든 포트(0~65535)가 차단되어있다.
 - AWS의 인바운드 규칙에서 22번 포트의 차단을 해제하여 외부에서 접속할 수 있도록 설정한다.
 - 22번 포트 : SSH (Secure Shell) 원격 접속 (AWS를 사용하기 위해 기본으로 열려있다.)
 - 하드웨어 자체를 직접 제어하긴 힘드므로 OS의 기능을 빌려 개발자는 제어한다. 이 때 개발자는 CLI를 제공하는 OS에서는 Shell 언어를 사용하여 파일과 하드웨어에 접근하여 사용한다.(22번 포트를 통한 원격 접속 및 명령)
 - Secure Shell은 원격 접속하는 과정에서 보안이 필요한 정보(password)들을 암호화, 복호화를 하여 사용하기 위해 사용한다.

 - 방화벽 인바운드 규칙에서 8080, 3306(MySQL) 등과 같은 포트를 따로 개방하여 외부로부터의 접속을 허가해준다.

<br>

## 5. RSA 공개키 암호화 방식

![RSA 공개키 암호화 방식](https://user-images.githubusercontent.com/84966961/170897610-043c4157-0ff5-43d6-8db4-41e5b2b11037.png)

<br>

## 6. SHELL 리눅스 명령어

### 6-1 기본 명령어

|명령어|설명|옵션 설명|
|-|-|-|
|`명령어` --help|명령어에 사용가능한 옵션 보기|명령어 설명서|
|^c(`ctrl + c`)|취소||
|clear|화면 라인 삭제||
|pwd|현재 위치 보기(print working directory)||
|ls|폴더 내 파일 list 보기|`-l` : 상세보기<br>`-시작:파일`<br>`d시작:폴더`<br>`-a` : 숨김 파일 포함 모두 보기<br>`-al` : 상세 모두 보기(옵션 다중 가능)|
|cd|디렉토리 이동(change directory)|절대경로 이동 가능(`/`로 시작)|
|mkdir|디렉토리 만들기(make directory)||
|touch|파일 만들기||
|rm|지우기(remove)|`-f` : 강제 삭제<br>`-r` : 폴더 내부의 파일들까지 삭제(폴더 삭제시 사용)|
|cp|복사(copy)|ex) `cp 복사할파일 복사후파일명`|
|mv|이동(move) 및 파일 이름 변경|ex) `mv 이동할파일 이동할상대경로`<br> `mv 이동할파일 이동한후파일이름`<br> `mv 이동할파일 이동할상대경로/이동한후파일이름`|
|ln|바로가기 파일 생성(link)|ex) `ln -s` (soft link-바로가기 파일 생성)|
|whoami|사용자명 확인|ex) `whoami`|

### 6-2 apt란?

  apt 는 node의 npm 기능과 같이 리눅스 운영체제에서 설치 가능한 프로그램들을 모아놓은 repository와 그 툴을 말한다. `패키지 관리 명령어 도구`이다.

#### 명령어 및 설명

|명령어|설명|옵션 설명|
|-|-|-|
|apt-cache search|패키지 검색|ex) `apt-cache search 검색하고싶은프로그램(저장소명)` |
|apt-cache list|저장된 저장소 모두 보기|ex) `apt-cache list` <br>너무 많이 나오므로 파이프라인과 함께 사용됨.<br>`apt-cache list | 강조하고싶은검색어`|
|apt install|패키지 설치|ex) `apt install 저장소명`: root 계정이 아닐 경우 권한이 없어 `denied`됨.<br>`sudo apt install 저장소명`를 통해 설치해야함.<br>`sudo apt install -y 저장소명`을 사용하면 설치 더블체크 과정이 사라짐. 바로 설치|
|apt update|패키지 메뉴판 갱신|ex) `apt update` : root 계정이 아닐 경우 권한이 없어 `denied`됨.<br>`sudo apt update`를 통해 갱신해야함.|
|tomcat9 설치 여부 확인 방법|
|netstat|포트 상태 확인|ex) `netstat -nlpt` : 초기시 net-tools를 설치해야 사용가능함.(`sudo apt install net-tools`)|


### 참조 : 리눅스 추가 설명
1. bin 폴더 - 실행파일이 모여있는 곳(각종 명령어 파일이 존재함.)
            - 바로가기 파일 또한 넣어둠.
2. apt <br>
```
  프로그램이 설치되는 과정(windows)
    1. 프로그램이 하드 디스크에 복사(위치)
    2. 윈도우 시작프로그램 등록
    3. 카톡-통신-방화벽 : 통신에 해당하는 port를 방화벽에서 열어주어야함.
    4. 윈도우 시작될 때 자동으로 시작
    5. 환경변수 설정 등등의 필요한 과정이 리눅스에서도 필요함.
    
  우분투(os)
    1. wget(명령어)을 통해 사이트에서 클라이언트를 다운로드 해야함.   
    2.  - 압축을 풀어야 함 (.tar 확장자)   
        - 설치해야 함 (.deb 확장자)   
    3. 위의 프로그램 설치 과정을 명령어로 수행해야함.
```
  `apt update` 명령어를 통해 `/etc/apt/sources.list` 의 레퍼지토리 경로를 업데이트하고 파일에 접근하여 설치하도록 할 수 있음.

  만약 list에 없는 파일을 다운로드하여 설치하고 싶다면 그리고 만약 ppa라는 개인 저장소에 올라와 있다면(릴리즈가 더 빠르게 되어있을 가능성이 있음. 그래서 apt엔 없지만 해당 프로그램이 존재할 가능성이 있음.) 각 os에서 `/etc/apt/sources.list.d/` 폴더에 등록한 후 설치
  
3. 파이프라인 `|` : 강조표시 기능

  `apt-cache search tomcat | grep tomcat` : 톰캣을 찾고 강조 표시

![image](https://user-images.githubusercontent.com/84966961/177953254-416e9c15-f9e3-461a-9584-2bffd0095d93.png)

4. netstat : 포트 상태 확인

  tomcat9 설치 후 기본 포트인 8080이 열려있는 것을 확인할 수 있다.

![image](https://user-images.githubusercontent.com/84966961/177955839-22ea1c74-7dc8-4f98-abe3-fac92737bb47.png)

5. AWS 보안 그룹 8080 포트 해제

  기존 python 프로젝트 flask 포트와 톰캣 8080 포트를 열어주었다.

![image](https://user-images.githubusercontent.com/84966961/177956445-503c44be-e39e-4496-a63d-3f36d436516f.png)

 브라우저에서 접근 가능.

![image](https://user-images.githubusercontent.com/84966961/177956806-eb7730a5-47ee-4950-b040-28e5d70dc555.png)