(function(para) {
  var p = para.sdk_url, n = para.name, w = window, d = document, s = 'script',x = null,y = null;
  if(typeof(w['sensorsDataAnalytic201505']) !== 'undefined') {
      return false;
  }
  w['sensorsDataAnalytic201505'] = n;
  w[n] = w[n] || function(a) {return function() {(w[n]._q = w[n]._q || []).push([a, arguments]);}};
  var ifs = ['track','quick','register','registerPage','registerOnce','trackSignup', 'trackAbtest', 'setProfile','setOnceProfile','appendProfile', 'incrementProfile', 'deleteProfile', 'unsetProfile', 'identify','login','logout','trackLink','clearAllRegister','getAppStatus'];
  for (var i = 0; i < ifs.length; i++) {
    w[n][ifs[i]] = w[n].call(null, ifs[i]);
  }
  if (!w[n]._t) {
    x = d.createElement(s), y = d.getElementsByTagName(s)[0];
    x.async = 1;
    x.src = p;
    x.setAttribute('charset','UTF-8');
    w[n].para = para;
    y.parentNode.insertBefore(x, y);
  }
})({
  //sdk_url: 'https://static.sensorsdata.cn/sdk/latest/sensorsdata.min.js',
  //heatmap_url: 'https://static.sensorsdata.cn/sdk/latest/heatmap.min.js',
  sdk_url: 'sensorsdata.full.js',
  heatmap_url: 'heatmap.full.js',
  name: 'sensors',
  //server_url: 'https://test-syg.datasink.sensorsdata.cn/sa?token=27f1e21b78daf376&project=lixiang',
  server_url: 'http://localhost:8085/api/params2',
  heatmap: {},
  send_type: 'ajax',
  show_log: true,
  scroll_notice_map:'not_collect',
  clickmap:'default',
});
sensors.quick('autoTrack'); //神策系统必须是1.4最新版及以上

//自定义数据
var i=0
sensors.registerPage({
  index: function() {
    return ++i; // 返回数字
  },
  istrue: function() {
    return i<10 ? true : false; // 返回bool
  },
  isEmptyString: function() {
    return ""; // 返回字符串
  },
  isDate: function() {
    return new Date('December 17, 1995 03:24:00'); // 返回日期类型
  },
  isArrayOfStr: function() {
    return ["1","2","3"] // 返回元素是字符串的数组
  }
})