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
# "Ajagoc" Android Studio �v���W�F�N�g �\�[�X
*************************************************************************
jagoclient v5.1 �� �A���h���C�h�ڐA�łł��B
(�I���W�i���� rene-grothmann@de ����쐬)�B 

�S�̃v���[���[�h������܂��B
(
����� YouTube �Đ����X�g������Q�Ƃ��Ă�������
https://www.youtube.com/watch?v=nUvaTV7lHGA&list=PL2clNB_BpXSXgLu7dGC8EtGH9xrGLn058
���j���[ --> �w���v --> Ajagoc? --> ����K�C�h���� �{�^�� �Ō����܂�
)

(1)WING�Ȃǂ̌�T�[�o�[�ɐڑ����đ΋ǁB
     �g�b�v��ʂŐڑ����I������"�ڑ�"�A"�����"��I�����đ΋ǂ�\�����ށB
     �I�������T�[�o�[��"�ڑ�"����O��"�ҏW"�� URL,�|�[�g�ԍ�,�G���R�[�f�B���O��ݒ肵�Ă�������
     �ڑ���� WING(wing.gr.jp) �̏ꍇ ���{��[���̓|�[�g=2525�A�G���R�[�f�B���O=MS932
     (English User�� port: encoding=UTF-8 �Ƃ��܂�)
     �΋ǈꗗ����A���l�̑΋ǂ��ϐ킷�邱�Ƃ��o���܂��B

     Link: WING --> http://www.wing.gr.jp/indexj.html
           ���h��-->http://jago.yamtom.com 

   �܂��ŏ��� '�ڑ�' �{�^���������� 'guest' �Ɠ��͂��Ă݂Ă��������B

(2)��T�[�o�[���o�R��������̃p�[�g�i�[�Ƒ΋ǁB
     �p�[�g�i�[��IP�A�h���X�͕ҏW�Őݒ肵�Ă����܂��B
     ���OWi-Fi �X�|�b�g�̃��[�^�[�͒ʏ� "�v���C�o�V�[�Z�p���[�^�[" �̐ݒ�ɂ��
     ���̃��[�^�[�Ɍp���������p�\�R�����m�̒ʐM���ł��Ȃ��Ȃ��Ă��܂��B
     �Q�� Wi-Fi �X�|�b�g�ԁA�ƒ��LAN�� W-Fi�X�|�b�g�Ԃł͐ڑ��ł��܂��B

     Android-4 �ȍ~�Ȃ�WiFi�_�C���N�g�Ŗ���LAN���[�^�[(���邢�̓A�N�Z�X�|�C���g)
     �Ȃ��ŋ@�퓯�m���P�΂P�ŋߋ����ڑ�(���P�O���܂œ͂��܂�)�ł��܂��A
     NFC�@�\���̒[���Ȃ�NFC�^�O�̕�����10cm�ȓ��ɋ߂Â��邾����
     ����WiFi�_�C���N�g�΋ǂł��܂�
     (��) WiFi �� WiFi�_�C���N�g �����p����Ɖ��������ɒx���Ȃ邱�Ƃ�����܂�
           ���̂悤�ȏꍇWiFi�_�C���N�g���Z�b�g���Ă�������(IP�ڑ��̃w���v�Q��)

     IP�ڑ�(LAN �Ȃ��� WiFi�_�C���N�g)���ł��Ȃ��ꍇ��
     (NFC+) Blutooth�ʐM�ł��΋ǂł��܂�

(3)���[�J���r���[
     �P�Ɍ�Ղł��B�l����▼�l���l�̊�������ׂ���A
     sgf�t�@�C����ǂ�ő΋ǂ��Č�������A�ω��}�𒲂ׂ��肵�܂�
     �[�������ʂȂ�Ζʂł̑΋ǂɂ��g���܂�

(4)���{�b�g�Ƃ̑ΐ�
     Agnugo �����肵�܂��B���o�[�W������ 3.8�ł�
     pachi-11.0 ���ǉ�����܂���(2016/10/01)
     fuego-1.1  ���ǉ�����܂���(2014/09/15)
     ray-8.0.1  ���ǉ�����܂���(2016/10/01)
     (Afuego(fuego�A���h���C�h��)�̃r���h���@�� www.geocities.jp/sakachin2/index.htm ���Q��)
                               sakachin2@gmail.com
