# AcneAnalyzer
<img src="https://user-images.githubusercontent.com/55984242/90117121-715ae480-dd91-11ea-994e-66f4f50acf64.PNG" height="140"></img>
<br>
**2020 DataScience Internship**
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
AcneAnalyzer의 시작 화면입니다. 실행 3초 후 메인 화면(진단하는 사진을 업로드 하는 화면)으로 넘어갑니다. 어플의 이름과 어플을 잘 나타내는 이미지로 구성했습니다.
<img src="https://user-images.githubusercontent.com/55984242/90116284-3a380380-dd90-11ea-8fde-8c8a761a3efb.jpg" height="400"></img><br>
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
AcneAnalyzer의 메인 화면입니다. 진단하려고 하는 이미지를 만들고 업로드 하며 업로드 후에는 진단 버튼을 눌러 진단을 시작합니다.
<img src="https://user-images.githubusercontent.com/55984242/90116385-5f2c7680-dd90-11ea-81fa-1cf2b0dd5742.jpg" height="400"></img>
<img src="https://user-images.githubusercontent.com/55984242/90116435-710e1980-dd90-11ea-9c82-e49e32c76b8d.jpg" height="400"></img>
<img src="https://user-images.githubusercontent.com/55984242/90116455-7b301800-dd90-11ea-98a7-e3f7034a2b6c.jpg" height="400"></img>
<img src="https://user-images.githubusercontent.com/55984242/90116482-83885300-dd90-11ea-8481-ec8036f76351.jpg" height="400"></img>
<img src="https://user-images.githubusercontent.com/55984242/90116515-90a54200-dd90-11ea-8ac6-82eb06a8fd9b.jpg" height="400"></img><br>
- **ADD SKIN PICTURE 버튼**을 누르면 카메라 또는 갤러리를 선택하는 다이얼로그가 생성됩니다. 
- **카메라**를 선택한 경우, 카메라로 원하는 부위의 사진을 찍으면 원본 사진이 갤러리에 저장되고, ‘acne image model’에서 분석하는 이미지의 크기만큼 자를 수 있도록 크롭 화면이 나타납니다. 원하는 부위를 크롭하면 크롭한 이미지가 갤러리와 분석할 대상 이미지로 추가됩니다. 
- **갤러리**를 선택한 경우, 갤러리에서 원하는 사진을 선택합니다. 카메라에서 찍은 사진을 계속해서 크롭할 수 있도록 원본을 저장하는 기능을 추가한 것입니다. 선택한 사진은 크롭 기능을 통해서 원하는 부위를 ‘acne image model’에서 분석할 수 있는 이미지 크기 만큼 자릅니다. 자른 이미지는 갤러리와 분석할 대상 이미지로 추가됩니다.
- 화면 우측 상단에 있는 **진단 버튼**을 누르면 리스트에 있는 이미지들이 분석되며 분석된 결과는 다음 화면으로 넘어갑니다.

### Result Screen
acne의 5가지 종류를 결과로 받아 화면을 생성합니다. acne에 대한 정보와 각 결과에 따른 치료법을 제시합니다.
<img src="https://user-images.githubusercontent.com/55984242/90116534-969b2300-dd90-11ea-8475-5bb76b3828e3.jpg" height="400"></img>
<img src="https://user-images.githubusercontent.com/55984242/90116542-9c910400-dd90-11ea-856d-da90ece0adf9.jpg" height="400"></img><br>
- 이미지에 대한 결과는 연속된 스트링(ex. "1, 2")으로 들어온 이미지 리스트에 대한 결과를 잘라서 사용합니다. acne 종류에 대한 조건문을 이용하여 각 이미지 분석 결과에 맞는 치료법을 제시합니다.
- 제목 옆 정보 버튼(i)을 누르면 여드름의 5가지 종류에 대한 다이얼로그 화면이 뜹니다. 각 여드름 이미지를 누르면 해당 타입에 대한 설명을 볼 수 있습니다.
