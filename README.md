# Ajagot1w.README.md
*************************************************************************
#"Ajagotc" Android Studio Project Source
*************************************************************************
This is jagoclient v5.1 porting to Android.
(Original was made by rene-grothmann@de.)

"Go" is a board game played from ancient times at east Asia.
There are 4 playing mode.
(
See  youtube movie list for operation:
https://www.youtube.com/watch?v=q5PgAf_rji0&list=PL2clNB_BpXSXl46OnjdelaBe78XbFXVWR
by "Menu-->Online Help-->Ajagoc?-->OperationGuide Moview button"
)

(1) Through Go server such as WING.
      Connect to selected server on top panel,
      request match to the selected 'foo' on 'who' panel.
      To connect to the server, push Edit button to set URL, port# and Encoding.
      WING server(wing.gr.jp) port is 1515 and encoding=UTF-8
      (2525 and encoding=MS932 for Japanese user). 
      set Language:en_US by TopPanel-ContextMenu:Options-->Set Language.
      Enter once "term e" command just after successful login.
      You can observe other player's match selecting from 'games' panel.

      Link WING:--> http://www.wing.gr.jp/indexe.html
           JagoClient:-->http://jagoclient.sourceforge.net

   At first, select server, push 'Connect' button then enter 'guest'.

(2) Match with the known partner.
      Without Go server, pear to pear matching with partner
      which IP address is known.
      (Device IP address is shown by Main Menu --> Partners)
      Almost public Wi-Fi spot rooter's setting of "Privacy Separator"
      will prohibit communication among terminals under it.
      Connection is available between two different spot
      or domestic LAN and Wi-Fi spot.

      For Android-4 device, WiFi Direct enable peer-to-peer IP connection without
      wireless rooter(or access point).
      WiFi Direct can reach distance of few 10 meters.
      For devices with NFC attachment, you can create WiFi Direct connection 
      by closing the part of NFC tag of the two devices in the range of 10 cm.

      You can create connection also by (NFC+)Bluetooth for peer-to-peer matching
      if IP connection is not available.

(3) LocalView
      This is simply a board view.
      Use it to resolve Composed-Go-Problem, study Go-Master's record,
      or review/make variation of sgf file.
      It may be for face-to-face matching if your terminal screen is big enough.

(4) Match with Robot
      Your opponent is Agnugo which is my Android porting of gnugo.
      Current GnuGo version is 3.8
      pachi-11.0 is added(2016/10/01)
      fuego-1.1  is added(2014/09/15)
      ray-8.0.1  is added(2016/10/01)
      (See www.geocities.jp/sakachin2/index.htm for Afuego(How to make fuego for Android.))
                               sakachin2@gmail.com

*************************************************************************
(Japanese)
# "Ajagoc" Android Studio プロジェクト ソース
*************************************************************************
jagoclient v5.1 の アンドロイド移植版です。
(オリジナルは rene-grothmann@de さん作成)。 

４つのプレーモードがあります。
(
操作は YouTube 再生リスト動画を参照してください
https://www.youtube.com/watch?v=nUvaTV7lHGA&list=PL2clNB_BpXSXgLu7dGC8EtGH9xrGLn058
メニュー --> ヘルプ --> Ajagoc? --> 操作ガイド動画 ボタン で見られます
)

(1)WINGなどの碁サーバーに接続して対局。
     トップ画面で接続先を選択して"接続"、"入場者"を選択して対局を申し込む。
     選択したサーバーに"接続"する前に"編集"で URL,ポート番号,エンコーディングを設定してください
     接続先は WING(wing.gr.jp) の場合 日本語端末はポート=2525、エンコーディング=MS932
     (English Userは port: encoding=UTF-8 とします)
     対局一覧から、他人の対局を観戦することも出来ます。

     Link: WING --> http://www.wing.gr.jp/indexj.html
           茶”碁-->http://jago.yamtom.com 

   まず最初は '接続' ボタンを押して 'guest' と入力してみてください。

(2)碁サーバーを経由せず特定のパートナーと対局。
     パートナーのIPアドレスは編集で設定しておきます。
     公衆Wi-Fi スポットのルーターは通常 "プライバシーセパレーター" の設定により
     そのルーターに継げた無線パソコン同士の通信ができなくなっています。
     ２つの Wi-Fi スポット間、家庭内LANと W-Fiスポット間では接続できます。

     Android-4 以降ならWiFiダイレクトで無線LANルーター(あるいはアクセスポイント)
     なしで機器同士が１対１で近距離接続(数１０ｍまで届きます)できます、
     NFC機能つきの端末ならNFCタグの部分を10cm以内に近づけるだけで
     すぐWiFiダイレクト対局できます
     (注) WiFi と WiFiダイレクト を混用すると応答が非常に遅くなることがあります
           そのような場合WiFiダイレクトリセットしてください(IP接続のヘルプ参照)

     IP接続(LAN ないし WiFiダイレクト)ができない場合は
     (NFC+) Blutooth通信でも対局できます

(3)ローカルビュー
     単に碁盤です。詰碁をや名人名人の棋譜を並べたり、
     sgfファイルを読んで対局を再現したり、変化図を調べたりします
     端末が大画面なら対面での対局にも使えます

(4)ロボットとの対戦
     Agnugo が相手します。現バージョンは 3.8です
     pachi-11.0 が追加されました(2016/10/01)
     fuego-1.1  が追加されました(2014/09/15)
     ray-8.0.1  が追加されました(2016/10/01)
     (Afuego(fuegoアンドロイド版)のビルド方法は www.geocities.jp/sakachin2/index.htm を参照)
                               sakachin2@gmail.com
