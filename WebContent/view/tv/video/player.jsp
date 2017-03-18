<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title></title>
    <script>
	  var mp = "";
      var sectionParam = "startTime:0,endTime:20000";
      var mediatype = ${mediatype};
      var playUrl = '${playurl}';

      mp = new MediaPlayer();	

      if (("0").equals(mediatype)) {
          mp.setCycleFlag(0); //0循环播放   1单次播放
          mp.setVideoDisplayArea(${left},${top}, ${width}, ${height});
          mp.setVideoDisplayMode(0);
          mp.refreshVideoDisplay();
          mp.setNativeUIFlag(1);
          mp.joinChannel(playUrl);
      }else{
          var json = '[{mediaUrl:"' + playUrl + '",';
          json += 'mediaCode:"1",';
          json += 'mediaType:2,';
          json += 'audioType:1,';
          json += 'videoType:3,';
          json += 'streamType:2,';
          json += 'drmType:1,';
          json += 'fingerPrint:0,';
          json += 'copyProtection:1,';
          json += 'allowTrickmode:1,';
          json += 'startTime:0,';
          json += 'endTime:20000,';
          json += 'entryID:"jsonentry1"}]';

          var instanceId = mp.getNativePlayerInstanceID();
          var playListFlag = 0;
          var videoDisplayMode = 0;
          var muteFlag = 0;
          var subtitleFlag = 0;
          var videoAlpha = 0;
          var cycleFlag = 1;
          var randomFlag = 0;
          var autoDelFlag = 0;
          var useNativeUIFlag = 1;
          var height = 348;
          var width = 615;
          var left = 60;
          var top = 119;
          mp.initMediaPlayer(instanceId, playListFlag, videoDisplayMode, height, width, left, top, muteFlag, useNativeUIFlag, subtitleFlag, videoAlpha, cycleFlag, randomFlag, autoDelFlag);

          mp.setCycleFlag(0); //0循环播放   1单次播放
          mp.setAllowTrickmodeFlag(1); //设置是否允许trick操作。 0:允许 1：不允许
          mp.setNativeUIFlag(0); //播放器是否显示缺省的Native UI，  0:不允许 1：允许
          mp.setAudioTrackUIFlag(0); //设置音轨的本地UI显示标志 0:不允许 1：允许
          mp.setMuteUIFlag(1); //设置静音的本地UI显示标志 0:不允许 1：允许

          mp.setSingleMedia(json);
          mp.setVideoDisplayMode(0);
          mp.setVideoDisplayArea(${left},${top}, ${width}, ${height});
          mp.refreshVideoDisplay();
          mp.playFromStart();
      }
      
      function stop(){
    	  mp.doStop();
      }
    </script>
<body bgcolor="transparent" onUnload="mp.doStop()">
</body>
</html>

