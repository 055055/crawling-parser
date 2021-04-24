# Crawling Parsing Data Project

### 프로젝트 설명
URL을 입력하여 전달 받은 HTML 문자열을 몫과 나머지로 출력

### 기능 요구 사항

+ 모든 문자 입력 가능
+ HTML 태그 제외 또는 TEXT 전체 옵션 선택
+ 영어 및 숫자만 출력
+ 오름차순 출력
  + 숫자 : 0,1,2 ... 9
  + 영어 : AaBbCc ... YyZz
+ 영어 숫자 Mix
  + 교차 출력 : 영어,숫자,영어,숫자,영어,숫자..
  

### 출력 예제
+ 교차 출력
 + 문자열 : 김#z0Aaa17bBZA?
 + 결과값 : A0a1a7BbZz

+ 출력 결과
 + 몫 : 묶음 단위 내 값
 + 나머지 : 묶음 단위 외 값


### 기술 
+ java 11
+ thymeleaf
+ spring-boot
+ spring-boot-starter-web
+ spring-boot-starter-validation
+ jsoup
+ sweetalert
+ jquery
+ bootstrap  
+ lombok
+ spring-boot-starter-test'


### 예제

+ 메인 화면
  <img width="1431" alt="메인화면" src="https://user-images.githubusercontent.com/42599161/115959045-39718700-a545-11eb-8f82-2cf1a2d20709.png">


+ 성공
  <img width="1434" alt="성공" src="https://user-images.githubusercontent.com/42599161/115959059-5017de00-a545-11eb-8133-3a5b47f6bc4b.png">
  
+ 실패
  <img width="1127" alt="실패" src="https://user-images.githubusercontent.com/42599161/115959130-87868a80-a545-11eb-87de-1b14486a35db.png">

 









