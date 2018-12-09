## 傳送門
[懶人包](./README.md#譯者整理編譯步驟)


# 免責聲明
Adhell3 只是一個使用 Samsung Knox SDK API 的應用程序。<br/>
要使用這些 API，需要 Knox SDK 和 KPE Development 許可金鑰。<br/>
這些是三星提供的屬性，而這些屬性在此儲存庫中並不提供，因此開發人員需要在接受三星提供的協議後，下載並獲取這些屬性。<br/>
如何使用此應用程序由開發人員負責，我不對此應用程式造成的任何損害承擔任何責任。<br/>

Knox SDK 可以在這裡下載： https://seap.samsung.com/sdk/knox-android <br/>
Knox License key 可以在這裡取得： https://seap.samsung.com/license-keys/create#section-knox-sdk <br/>
API 可以在這裡找到： https://seap.samsung.com/api-references/android/reference/packages.html


# 背景
最初的 Adhell 是由三星的開發人員開發的。在被三星強行從網路上移除代碼後，FiendFyre 提供了加強版本的 Adhell2。但過了一段時間，它也停止更新了<br/>
Adhell3 是之前停用的 Adhell2 應用程式的擴展，具有更多附加功能。


## 功能
- 行動網路和 Wi-Fi<br/>
可以指定程式禁止使用行動網路或 Wi-Fi。例如可以避免意外地使用行動網路觀看影片。

- 自訂防火牆規則<br/>
攔截`所有IP地址`的`53`埠在 `Chrome瀏覽器` 投放廣告：<br/><br/>
    `com.android.chrome|*|53`

- 指定程式白名單網址<br/>
當某個網域需要全部攔截，但您需要在特定程式上使用此網域，否則該程式將無法正常工作。<br/>
不必將程式加入白名單，只需要加入將此網域和程式包名。<br/>
例如：攔截 `graph.facebook.com`，但是 Facebook Messenger 登入需要連到這個網域，那麼可以輸入規則：<br/><br/>
    `com.facebook.orca|graph.facebook.com`

- 支援本機 host 來源<br/>
主機文件可以位於內部或外部儲存上。<br/>
例如使用存放在內建記憶體的 host.txt 檔案：<br/>
    `file:///mnt/sdcard/hosts.txt`

- 顯示 host 來源的內容<br/>
顯示單個 host 來源的網域列表或所有已攔截網域的列表。<br/>
這可以方便檢查特定網域是否已在列表中。<br/>
此列表不計算重複的網域。

- 備份還原資料庫<br/>
備份本程式的資料庫到內建儲存空間，突然發生問題時還能夠還原。

- 為個別程式設定 DNS<br/>
選擇程式使用自訂 DNS。

## 建立 apk 的準備工作
### Java
- 安裝 JDK 8：http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- 不要使用 JDK 9，因為 Gradle 會有問題。查看 Issue 78 https://gitlab.com/fusionjack/adhell3/issues/78.
 
### Git (可選，推薦)
- 安裝 git：https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

### 原始碼
- 使用 git: 使用以下程式碼複製 `git clone https://github.com/david082321/fusionjack-adhell3.git`
- 不用 git: 下載原始碼為zip檔案： https://github.com/david082321/fusionjack-adhell3/archive/master.zip
- 在 `app` 資料夾中建立檔案 `app.properties`。即：`app\app.properties`
- 將 `package.name=這裡改成你的安裝包名` 放在檔案的第一行

### Android Studio
- 下載並安裝最新的 Android Studio https://developer.android.com/studio/index.html
- 在 Android Studio 中打開 Adhell3 項目
- 安裝缺少的SDK、build-tools和其他提示的東西
- 如果出現以下錯誤 `Configuration on demand is not supported`，請看此評論解決： https://gitlab.com/fusionjack/adhell3/commit/1fb8ea98cf43507b32db56d9fb584b33dc6579f1#note_74463246

### Knox SDK
- 下載最新的 Knox SDK 和 supportlib https://seap.samsung.com/sdk/knox-android
- 在 `app` 資料夾中建立 `libs` 子資料夾。即：`app\libs`
- 解壓前面下載的兩個zip檔案
- 將 supportlib 開頭的 jar 檔案改名為 `supportlib.jar`
- `knoxsdk.jar` 在解壓後的 libs 資料夾裡面
- 將上面兩個 jar 檔案放到 `app\libs` 資料夾

## 自訂
### 修改預設只有1萬5個網域的上限
* 將 `domain.limit` 寫入 `app.properties`，例如 `domain.limit=50000`

### 將許可金鑰和 backwards-compatible key 內建在 Adhell3 之中
* 將 `skl.key` 寫入 `app.properties`，例如：`skl.key=KLM06-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX`
* 將 `backwards.key` 寫入 `app.properties`，例如：`backwards.key=B6B2BXXXXXXXXXXXXXXXX`
* 如果您使用這些屬性，請在分發應用程式時注意，因為金鑰是純文字儲存。
* 這只是為了方便起見，您在自己裝置上測試時無需將金鑰放在其他地方，因此這僅適用於新安裝。<br/>
(譯注：因為每個key只能使用10個裝置，超過將會所有裝置都不能用，而且還是要等3個月才能再次申請。少數人或自用還可以，公開發佈時建議不要使用這個功能。)

### 網域 prefix
* Prefix all domains (with the exception of Filter Lists) with * or nothing.
* Valid `domain.prefix` options: `domain.prefix=true`, `domain.prefix=false`
* If you choose not to define a prefixing option, domains will not be prefixed.
* Put `domain.prefix` in `app.properties`
* `domain.prefix=true` -> prefix all domains with `*`
* `domain.prefix=false` -> don't prefix anything, keep domains as they are
* nothing -> no prefix

### 隱藏功能
* 請注意，啟用某些隱藏功能可能會導致設備出現故障（如果未採取預防措施，尤其是在停用系統程式時）。啟用它們需要您自擔風險。 
* 將 `enable.disableApps=true` 寫入 `app.properties` -> 以啟用'停用程式'功能：<br/>
An ability to disable user or system applications entirely
* 將 `enable.appComponent=true` 寫入 `app.properties` -> 以啟用'程式元件'功能：<br/>
An ability to disable app's permissions, services and receivers. Only user apps are supported.

## 如何建立 apk

### 使用 Git
連上設備並在 bash 控制台中執行以下指令：<br/>
1. `cd adhell3`<br/>
2. `git stash && git pull --rebase && git stash pop`<br/>
3. `bash gradlew clean installDebug`

Explanation:
1. Enter adhell3 folder
2. It stores your changes, e.g package name, updates the source code and re-apply your changes
3. Build and install apk on the device

### 不用 Git
1. Re-download the source code as a zip file and re-applies your changes manually<br/>
2. 連上設備並在 Windows 控制台中執行以下指令：<br/>
`cd adhell3`<br/>
`gradlew clean installDebug`


## 準備開始使用 Adhell3
您需要 KPE Development license key 才能使用 Adhell3。<br/>
您需要註冊成為開發人員才能取得此許可。對於開發人員，許可證需要每3個月更新一次<br/>
當您收到有關許可證到期的郵件時，通常無法立即生成新金鑰，您需要等待幾天。</br>
在此期間，Adhell3 仍然正常執行。如果金鑰不能使用，Adhell3 將顯示開通對話框。這次你應該能夠生成一個新金鑰。

- 在此註冊為開發人員： https://seap.samsung.com/enrollment
- 在此生成許可證金鑰： https://seap.samsung.com/license-keys/create#section-knox-sdk
- 隨便輸入名稱(alias name)，建議不要有adhell相關字眼
- 按下 `Generate License Key`、`Accept`
- 然後會提供你兩個金鑰：KPE key 和 backwards-compatible key<br/>
使用 Knox 2.8 及更高版本，只需要 KPE key<br/>
使用 Knox 2.7.1 及更低版本，則需要 backwards-compatible key


## Credits
* Adhell3 is based on FiendFyre's Adhell2 which is heavily modified by me.<br/>
* Big thanks to @mmotti who provides a host file for Adhell3. You can visit his Github here: https://github.com/mmotti/mmotti-host-file
* Adhell3 is using icons from https://material.io/icons
* Chinese Translation: @david082321


# 譯者整理編譯步驟
## 前置作業
1. 安裝 JDK 8：http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. 安裝 Android Studio https://developer.android.com/studio/index.html
## 編譯
3. 下載原始碼：https://github.com/david082321/Adhell3/archive/master.zip
4. 下載並正確放置[KNOX](./README.md#Knox SDK)
5. 開啟原始碼裡面的 `app\app.properties`
6. 第一行改成你的包名，注意不能有中文，只能有英文、數字和英文句點。不能由數字開頭，不能由英文句點結尾，英文句點後第一個不能是數字。
7. 第二行是`網域數量限制`。（預設15000，我改成10萬了）
8. 第三行是`停用程式`功能。如果需要開啟，請改成 `enable.disableApps=true`
9. 第四行是`程式元件`功能。如果需要開啟，請改成 `enable.appComponent=true`
10. 第五行是`網域前綴`功能。如果需要開啟，請改成 `domain.prefix=true`
11. 儲存`app.properties`。
12. 在 Android Studio 中打開 Adhell3 項目
13. 安裝缺少的SDK、build-tools和其他提示的東西
- ps. 如果出現以下錯誤 `Configuration on demand is not supported`，請看此評論解決： https://gitlab.com/fusionjack/adhell3/commit/1fb8ea98cf43507b32db56d9fb584b33dc6579f1#note_74463246
14. 完成後，再進行以下步驟。
15. (Windows系統) 按住Shift按鍵，右鍵選擇剛才下載的`Adhell3-master`資料夾，選擇在此開啟命令提示視窗。
- ps. 或是可以 `Win + R` -> `cmd` -> `確定` -> `cd C:/Download/Adhell3-master` (以你電腦的資料夾路徑取代)
16. 手機開啟 `adb偵錯`，並且連上電腦，給予授權。
17. 輸入指令 `gradlew clean installDebug`，然後按 `Enter`。
18. 等待安裝完成。
19. [開始使用](./README.md#準備開始使用-Adhell3)。
