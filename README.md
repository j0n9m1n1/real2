# real2
e강의동 과제 알림 어플리케이션(선문대학교)

과제, 시간표, 학사일정,강의자료 등등  새로운 글이 올라오면 푸시알림으로 알려주기

시간표, 캘린더, 강의자료는 pdf viewer

일정에는 자신의것만 아니면 학사일정까지
또는 개강 중간 기간 종강만 수강신청 관련

flow
client에서 server로 
server에서 db로
db에서 server로
server에서 client로

shared preference 자동로그인



학교본관419호 041 530 8993

토큰을 쉐어드 프리퍼런스에 넣고
로그인 할때 아이디랑 서버에 전송하고 그 두개를 디비에 저장 해놔야 다음에 보낼수 ㅇㅅ을듯
폴더 나누기(패키지 우클릭 -> new -> package -> 소스옮기면서 refactor하면 됌한단어 소문자)
클래스 대문자 시작
라이센스 목록 기재

registration_ids -> to

-GSON
gson serializedname nested


멀티프로세싱, 백그라운드 태스크

0. onMessageRecieved 들어가게(포기)
1. 최근 알림 목록 (o)
2. 프래그먼트 누를때마다 get_report 요청하는거 (o)
3. 60분 마다 get_report 요청 (되는듯)
4. 과제 가져올때로딩 창 만드렁주기(o)
5. 로그인후 뒤로가기 불가하게(o)
6. 백그라운드가 아닐때도 푸시 받기(o)
7. 자동로그인 (시간 아까워)
8. 네비게이션 드로어 ()
9. 액션바, 바탕 전부 #FFFFFF으로 만들어 주기 글씨만 #000000
10. 남은 기간의 과제만 response (x 시간없어)
11. 그리고 dictionary를 다른 함수에서 사용 할 수 있는지 판단 해야함(o)
12. 클라, 서버 코드 최적화(서버o, 클라x)
13. AWS에 프로젝트 배포(o)
14. 마켓 앱 등록(x(캡스톤 신청X 돈 없어))

네비게이션 드로어 

로그아웃
설정

sudo apt-get install screen
screen

Hit enter. Now it's like you're in a different terminal window.

python manage.py runserver 0.0.0.0:8000

Now you're running the server, and you'd like to get back to your first screen while letting the django app continue running. Screen has a nice feature built-in for that. To get back to your main terminal type:

ctrl+a d

From there, you can get back to the django screen by typing:

screen -r

If you have multiple screens open you can reach the correct one by it's 4-5 digit ID number:

screen -r 1333
And the man pages are pretty good:

man screen
