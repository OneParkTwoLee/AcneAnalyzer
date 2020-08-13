# AcneAnalyzer
<img src="https://user-images.githubusercontent.com/55984242/90117121-715ae480-dd91-11ea-994e-66f4f50acf64.PNG" height="150"></img>
<br>
2020 DataScience Internship
> 여드름 피부로 고민하는 모두에게, 여드름 이미지를 분석하여 6가지 타입으로 분류하고 현재 피부 상태를 알려주는 어플입니다.

## 개발환경
Android Studio SDK 28

## 팀원 및 역할
| 팀원 | 역할 |
| --- | --- |
|이동찬|백엔드, 이미지 분석 모델 구현, 데이터 수집|
|이준영|UI 디자인, 프론트엔드, 데이터 수집|
|박소영|백엔드, 이미지 분석 모델 구현, 데이터 수집|

## How it works
### Splash Screen
<img src="https://user-images.githubusercontent.com/55984242/90116284-3a380380-dd90-11ea-8fde-8c8a761a3efb.jpg" height="400"></img><br>
AcneAnalyzer의 시작 화면입니다. 실행 3초 후 메인 화면(진단하는 사진을 업로드 하는 화면)으로 넘어갑니다. 어플의 이름과 어플을 잘 나타내는 이미지로 구성했습니다.
```
new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                , 3000);
```
### Main Screen
### Result Screen
