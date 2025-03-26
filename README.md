# Shattered Pixel Dungeon Seed Finder (KR 한글판)

녹픽던에서 특정 조건을 만족하는 시드를 찾아주는 프로그램 (예: 첫 네 층에서 글레이브 +3과 판금 갑옷).

특정 시드에서 층마다 나오는 아이템들 (상점 내용 포함) 을 알려주는 기능도 있음.

- REFERRED FROM [🔗Elektrochecker seed finder](https://github.com/Elektrochecker/shpd-seed-finder)

# 사용법
### ⭐ 꼭 전체화면 옵션 끄고 시드파인더 돌리기!!

## 시드 분석

#### 메인화면 > 시드 분석 > 시드 입력 > 결과 출력

## 시드 찾기

#### 메인화면 > 시드 찾기 > 조건 입력 > 대기 > 결과 출력

- 조건 입력법:
  - 첫 줄: 탐색할 층 (필수X, 첫줄부터 아이템 입력 시 30층을 기준으로 함),
  - 줄바꿈(Enter)으로 아이템을 구분해서 입력.

#### 마법부여나 상형문자 붙은 아이템도 검색 가능.

예) 육중함의 미늘 갑옷 +1

예2) 반 엔트로피의 판금 갑옷 +3

예3) 짜릿한 메이스 +2

#### "큰따옴표"로 감싸 정확한 이름 검색 가능.

예) "검"

#### 실제 아이템 이름에 입력한 아이템 이름이 포함되면 결과를 반환하기 떄문에, 다양하게 검색이 가능함.

예1) +4

예2) 판금


#### 띄어쓰기를 무시하고 검색하므로, 띄어쓰기 이슈는 걱정안해도 됨.

예1) 판 금갑옷

예2) 썩은열 매의씨 앗

예3) 돌    건  틀  릿 +             3

## 시간이 너무 오래 걸려서 끄고 싶을 때..

**PC**: 그냥 X 눌러 끄기 (업데이트됨) ~~작업 관리자에서 강제 종료, 등~~

**Mobile**: 그냥 끄고 최근앱 탭에서 지우기 (업데이트됨) ~~설정 > 앱 에서 강제 종료, 등~~

## CMD (Item)

### ⭐ 줄바꿈으로 여러 명령어 동시 입력 가능

- **/give** [item] (amount) (upgrade)
> /give rings.RingOfWealth 1 50000

##### (아이템명은 [여기에서](https://github.com/00-Evan/shattered-pixel-dungeon/tree/master/core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/items))

- **/summon** [mob]
> /summon YogDzewa

> /summon npcs.Shopkeeper

##### (몹이름은 [여기에서](https://github.com/00-Evan/shattered-pixel-dungeon/tree/master/core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/actors/mobs))

- **/key** [IronKey/GoldenKey/CrystalKey/SkeletonKey/쇠열쇠/황금열쇠/수정열쇠/해골열쇠] (floor)

- **/floor** [floor]  🐛 경고: 고치는중

- **/log** [type] [msg]  🐛 경고: 고치는중


# How to Build

1. 이 레포 클론(clone)

```
git clone https://github.com/Llyias/shpd-seedFinder-ko/
```

2. 알아서 머지(merge)

3. 알아서 빌드
- EXE (Win)/ .APP (Mac)
  - `./gradlew desktop:jpackage`
- JAR
  - `./gradlew desktop:release`
- APK
  - 안드로이드 스튜디오: `Build > Generate Signed App Bundle or APK > APK`


# Shattered Pixel Dungeon

[Shattered Pixel Dungeon](https://shatteredpixel.com/shatteredpd/) is an open-source traditional roguelike dungeon crawler with randomized levels and enemies, and hundreds of items to collect and use. It's based on the [source code of Pixel Dungeon](https://github.com/00-Evan/pixel-dungeon-gradle), by [Watabou](https://www.watabou.ru).

Shattered Pixel Dungeon currently compiles for Android, iOS, and Desktop platforms. You can find official releases of the game on:

[![Get it on Google Play](https://shatteredpixel.com/assets/images/badges/gplay.png)](https://play.google.com/store/apps/details?id=com.shatteredpixel.shatteredpixeldungeon)
[![Download on the App Store](https://shatteredpixel.com/assets/images/badges/appstore.png)](https://apps.apple.com/app/shattered-pixel-dungeon/id1563121109)
[![Steam](https://shatteredpixel.com/assets/images/badges/steam.png)](https://store.steampowered.com/app/1769170/Shattered_Pixel_Dungeon/)<br>
[![GOG.com](https://shatteredpixel.com/assets/images/badges/gog.png)](https://www.gog.com/game/shattered_pixel_dungeon)
[![Itch.io](https://shatteredpixel.com/assets/images/badges/itch.png)](https://shattered-pixel.itch.io/shattered-pixel-dungeon)
[![Github Releases](https://shatteredpixel.com/assets/images/badges/github.png)](https://github.com/00-Evan/shattered-pixel-dungeon/releases)

If you like this game, please consider [supporting me on Patreon](https://www.patreon.com/ShatteredPixel)!

There is an official blog for this project at [ShatteredPixel.com](https://www.shatteredpixel.com/blog/).

The game also has a translation project hosted on [Transifex](https://www.transifex.com/shattered-pixel/shattered-pixel-dungeon/).

Note that **this repository does not accept pull requests!** The code here is provided in hopes that others may find it useful for their own projects, not to allow community contribution. Issue reports of all kinds (bug reports, feature requests, etc.) are welcome.

If you'd like to work with the code, you can find the following guides in `/docs`:
- [Compiling for Android.](docs/getting-started-android.md)
    - **[If you plan to distribute on Google Play please read the end of this guide.](docs/getting-started-android.md#distributing-your-apk)**
- [Compiling for desktop platforms.](docs/getting-started-desktop.md)
- [Compiling for iOS.](docs/getting-started-ios.md)
- [Recommended changes for making your own version.](docs/recommended-changes.md)
