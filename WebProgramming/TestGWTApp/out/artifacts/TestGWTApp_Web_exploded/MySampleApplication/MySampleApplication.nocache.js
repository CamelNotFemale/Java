function MySampleApplication(){
  var $intern_0 = 'bootstrap', $intern_1 = 'begin', $intern_2 = 'gwt.codesvr.MySampleApplication=', $intern_3 = 'gwt.codesvr=', $intern_4 = 'MySampleApplication', $intern_5 = 'startup', $intern_6 = 'DUMMY', $intern_7 = 0, $intern_8 = 1, $intern_9 = 'iframe', $intern_10 = 'position:absolute; width:0; height:0; border:none; left: -1000px;', $intern_11 = ' top: -1000px;', $intern_12 = 'CSS1Compat', $intern_13 = '<!doctype html>', $intern_14 = '', $intern_15 = '<html><head><\/head><body><\/body><\/html>', $intern_16 = 'undefined', $intern_17 = 'readystatechange', $intern_18 = 10, $intern_19 = 'script', $intern_20 = 'javascript', $intern_21 = 'Failed to load ', $intern_22 = 'moduleStartup', $intern_23 = 'scriptTagAdded', $intern_24 = 'moduleRequested', $intern_25 = 'meta', $intern_26 = 'name', $intern_27 = 'MySampleApplication::', $intern_28 = '::', $intern_29 = 'gwt:property', $intern_30 = 'content', $intern_31 = '=', $intern_32 = 'gwt:onPropertyErrorFn', $intern_33 = 'Bad handler "', $intern_34 = '" for "gwt:onPropertyErrorFn"', $intern_35 = 'gwt:onLoadErrorFn', $intern_36 = '" for "gwt:onLoadErrorFn"', $intern_37 = '#', $intern_38 = '?', $intern_39 = '/', $intern_40 = 'img', $intern_41 = 'clear.cache.gif', $intern_42 = 'baseUrl', $intern_43 = 'MySampleApplication.nocache.js', $intern_44 = 'base', $intern_45 = '//', $intern_46 = 'locale', $intern_47 = 'default', $intern_48 = 'locale=', $intern_49 = 7, $intern_50 = '&', $intern_51 = '__gwt_Locale', $intern_52 = '_', $intern_53 = 'Unexpected exception in locale detection, using default: ', $intern_54 = 2, $intern_55 = 'user.agent', $intern_56 = 'webkit', $intern_57 = 'safari', $intern_58 = 'msie', $intern_59 = 11, $intern_60 = 'ie10', $intern_61 = 9, $intern_62 = 'ie9', $intern_63 = 8, $intern_64 = 'ie8', $intern_65 = 'gecko', $intern_66 = 'gecko1_8', $intern_67 = 3, $intern_68 = 4, $intern_69 = 'selectingPermutation', $intern_70 = 'MySampleApplication.devmode.js', $intern_71 = 'en', $intern_72 = '0F0A12308305FEC7A81415A4FDDA784D', $intern_73 = '13900B238CC8704F0A3D87119EB850A2', $intern_74 = '3AEBB079FA0729F28B2D369E7BAEDA6A', $intern_75 = '470B80247BAF73CE3BAE8742CE833475', $intern_76 = 'ru', $intern_77 = '474F269CC72B8482C418C0ECBAD73A01', $intern_78 = '48FD2A4DC275C309C4564BED2758E6C1', $intern_79 = '64A28C2E536B7D2E61BCCBBFF47CD5F3', $intern_80 = '65DBD70D8485B20C0214F2452A8CDCE7', $intern_81 = '70043A81C55979471EB3B77069AD0A94', $intern_82 = '999CCA01BF7EDDCE833C9546336039EF', $intern_83 = 'A150B83EC0BC281433CEFFC268659C03', $intern_84 = 'B7A73A9B310F1EEB16ACAA3C12EB8201', $intern_85 = 'D08E7DD3954159428060B1DD3898300B', $intern_86 = 'ED92B5B500471C81E5C256891ECF3001', $intern_87 = 'FA00EDD24769C78AB54874929A3D2229', $intern_88 = ':', $intern_89 = '.cache.js', $intern_90 = 'loadExternalRefs', $intern_91 = 'end', $intern_92 = 'http:', $intern_93 = 'file:', $intern_94 = '_gwt_dummy_', $intern_95 = '__gwtDevModeHook:MySampleApplication', $intern_96 = 'Ignoring non-whitelisted Dev Mode URL: ', $intern_97 = ':moduleBase', $intern_98 = 'head';
  var $wnd = window;
  var $doc = document;
  sendStats($intern_0, $intern_1);
  function isHostedMode(){
    var query = $wnd.location.search;
    return query.indexOf($intern_2) != -1 || query.indexOf($intern_3) != -1;
  }

  function sendStats(evtGroupString, typeString){
    if ($wnd.__gwtStatsEvent) {
      $wnd.__gwtStatsEvent({moduleName:$intern_4, sessionId:$wnd.__gwtStatsSessionId, subSystem:$intern_5, evtGroup:evtGroupString, millis:(new Date).getTime(), type:typeString});
    }
  }

  MySampleApplication.__sendStats = sendStats;
  MySampleApplication.__moduleName = $intern_4;
  MySampleApplication.__errFn = null;
  MySampleApplication.__moduleBase = $intern_6;
  MySampleApplication.__softPermutationId = $intern_7;
  MySampleApplication.__computePropValue = null;
  MySampleApplication.__getPropMap = null;
  MySampleApplication.__installRunAsyncCode = function(){
  }
  ;
  MySampleApplication.__gwtStartLoadingFragment = function(){
    return null;
  }
  ;
  MySampleApplication.__gwt_isKnownPropertyValue = function(){
    return false;
  }
  ;
  MySampleApplication.__gwt_getMetaProperty = function(){
    return null;
  }
  ;
  var __propertyErrorFunction = null;
  var activeModules = $wnd.__gwt_activeModules = $wnd.__gwt_activeModules || {};
  activeModules[$intern_4] = {moduleName:$intern_4};
  MySampleApplication.__moduleStartupDone = function(permProps){
    var oldBindings = activeModules[$intern_4].bindings;
    activeModules[$intern_4].bindings = function(){
      var props = oldBindings?oldBindings():{};
      var embeddedProps = permProps[MySampleApplication.__softPermutationId];
      for (var i = $intern_7; i < embeddedProps.length; i++) {
        var pair = embeddedProps[i];
        props[pair[$intern_7]] = pair[$intern_8];
      }
      return props;
    }
    ;
  }
  ;
  var frameDoc;
  function getInstallLocationDoc(){
    setupInstallLocation();
    return frameDoc;
  }

  function setupInstallLocation(){
    if (frameDoc) {
      return;
    }
    var scriptFrame = $doc.createElement($intern_9);
    scriptFrame.id = $intern_4;
    scriptFrame.style.cssText = $intern_10 + $intern_11;
    scriptFrame.tabIndex = -1;
    $doc.body.appendChild(scriptFrame);
    frameDoc = scriptFrame.contentWindow.document;
    frameDoc.open();
    var doctype = document.compatMode == $intern_12?$intern_13:$intern_14;
    frameDoc.write(doctype + $intern_15);
    frameDoc.close();
  }

  function installScript(filename){
    function setupWaitForBodyLoad(callback){
      function isBodyLoaded(){
        if (typeof $doc.readyState == $intern_16) {
          return typeof $doc.body != $intern_16 && $doc.body != null;
        }
        return /loaded|complete/.test($doc.readyState);
      }

      var bodyDone = isBodyLoaded();
      if (bodyDone) {
        callback();
        return;
      }
      function checkBodyDone(){
        if (!bodyDone) {
          if (!isBodyLoaded()) {
            return;
          }
          bodyDone = true;
          callback();
          if ($doc.removeEventListener) {
            $doc.removeEventListener($intern_17, checkBodyDone, false);
          }
          if (onBodyDoneTimerId) {
            clearInterval(onBodyDoneTimerId);
          }
        }
      }

      if ($doc.addEventListener) {
        $doc.addEventListener($intern_17, checkBodyDone, false);
      }
      var onBodyDoneTimerId = setInterval(function(){
        checkBodyDone();
      }
      , $intern_18);
    }

    function installCode(code_0){
      var doc = getInstallLocationDoc();
      var docbody = doc.body;
      var script = doc.createElement($intern_19);
      script.language = $intern_20;
      script.src = code_0;
      if (MySampleApplication.__errFn) {
        script.onerror = function(){
          MySampleApplication.__errFn($intern_4, new Error($intern_21 + code_0));
        }
        ;
      }
      docbody.appendChild(script);
      sendStats($intern_22, $intern_23);
    }

    sendStats($intern_22, $intern_24);
    setupWaitForBodyLoad(function(){
      installCode(filename);
    }
    );
  }

  MySampleApplication.__startLoadingFragment = function(fragmentFile){
    return computeUrlForResource(fragmentFile);
  }
  ;
  MySampleApplication.__installRunAsyncCode = function(code_0){
    var doc = getInstallLocationDoc();
    var docbody = doc.body;
    var script = doc.createElement($intern_19);
    script.language = $intern_20;
    script.text = code_0;
    docbody.appendChild(script);
  }
  ;
  function processMetas(){
    var metaProps = {};
    var propertyErrorFunc;
    var onLoadErrorFunc;
    var metas = $doc.getElementsByTagName($intern_25);
    for (var i = $intern_7, n = metas.length; i < n; ++i) {
      var meta = metas[i], name_0 = meta.getAttribute($intern_26), content_0;
      if (name_0) {
        name_0 = name_0.replace($intern_27, $intern_14);
        if (name_0.indexOf($intern_28) >= $intern_7) {
          continue;
        }
        if (name_0 == $intern_29) {
          content_0 = meta.getAttribute($intern_30);
          if (content_0) {
            var value_0, eq = content_0.indexOf($intern_31);
            if (eq >= $intern_7) {
              name_0 = content_0.substring($intern_7, eq);
              value_0 = content_0.substring(eq + $intern_8);
            }
             else {
              name_0 = content_0;
              value_0 = $intern_14;
            }
            metaProps[name_0] = value_0;
          }
        }
         else if (name_0 == $intern_32) {
          content_0 = meta.getAttribute($intern_30);
          if (content_0) {
            try {
              propertyErrorFunc = eval(content_0);
            }
             catch (e) {
              alert($intern_33 + content_0 + $intern_34);
            }
          }
        }
         else if (name_0 == $intern_35) {
          content_0 = meta.getAttribute($intern_30);
          if (content_0) {
            try {
              onLoadErrorFunc = eval(content_0);
            }
             catch (e) {
              alert($intern_33 + content_0 + $intern_36);
            }
          }
        }
      }
    }
    __gwt_getMetaProperty = function(name_0){
      var value_0 = metaProps[name_0];
      return value_0 == null?null:value_0;
    }
    ;
    __propertyErrorFunction = propertyErrorFunc;
    MySampleApplication.__errFn = onLoadErrorFunc;
  }

  function computeScriptBase(){
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf($intern_37);
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf($intern_38);
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf($intern_39, Math.min(queryIndex, hashIndex));
      return slashIndex >= $intern_7?path.substring($intern_7, slashIndex + $intern_8):$intern_14;
    }

    function ensureAbsoluteUrl(url_0){
      if (url_0.match(/^\w+:\/\//)) {
      }
       else {
        var img = $doc.createElement($intern_40);
        img.src = url_0 + $intern_41;
        url_0 = getDirectoryOfFile(img.src);
      }
      return url_0;
    }

    function tryMetaTag(){
      var metaVal = __gwt_getMetaProperty($intern_42);
      if (metaVal != null) {
        return metaVal;
      }
      return $intern_14;
    }

    function tryNocacheJsTag(){
      var scriptTags = $doc.getElementsByTagName($intern_19);
      for (var i = $intern_7; i < scriptTags.length; ++i) {
        if (scriptTags[i].src.indexOf($intern_43) != -1) {
          return getDirectoryOfFile(scriptTags[i].src);
        }
      }
      return $intern_14;
    }

    function tryBaseTag(){
      var baseElements = $doc.getElementsByTagName($intern_44);
      if (baseElements.length > $intern_7) {
        return baseElements[baseElements.length - $intern_8].href;
      }
      return $intern_14;
    }

    function isLocationOk(){
      var loc = $doc.location;
      return loc.href == loc.protocol + $intern_45 + loc.host + loc.pathname + loc.search + loc.hash;
    }

    var tempBase = tryMetaTag();
    if (tempBase == $intern_14) {
      tempBase = tryNocacheJsTag();
    }
    if (tempBase == $intern_14) {
      tempBase = tryBaseTag();
    }
    if (tempBase == $intern_14 && isLocationOk()) {
      tempBase = getDirectoryOfFile($doc.location.href);
    }
    tempBase = ensureAbsoluteUrl(tempBase);
    return tempBase;
  }

  function computeUrlForResource(resource){
    if (resource.match(/^\//)) {
      return resource;
    }
    if (resource.match(/^[a-zA-Z]+:\/\//)) {
      return resource;
    }
    return MySampleApplication.__moduleBase + resource;
  }

  function getCompiledCodeFilename(){
    var answers = [];
    var softPermutationId = $intern_7;
    function unflattenKeylistIntoAnswers(propValArray, value_0){
      var answer = answers;
      for (var i = $intern_7, n = propValArray.length - $intern_8; i < n; ++i) {
        answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
      }
      answer[propValArray[n]] = value_0;
    }

    var values = [];
    var providers = [];
    function computePropValue(propName){
      var value_0 = providers[propName](), allowedValuesMap = values[propName];
      if (value_0 in allowedValuesMap) {
        return value_0;
      }
      var allowedValuesList = [];
      for (var k in allowedValuesMap) {
        allowedValuesList[allowedValuesMap[k]] = k;
      }
      if (__propertyErrorFunction) {
        __propertyErrorFunction(propName, allowedValuesList, value_0);
      }
      throw null;
    }

    providers[$intern_46] = function(){
      var locale = null;
      var rtlocale = $intern_47;
      try {
        if (!locale) {
          var queryParam = location.search;
          var qpStart = queryParam.indexOf($intern_48);
          if (qpStart >= $intern_7) {
            var value_0 = queryParam.substring(qpStart + $intern_49);
            var end = queryParam.indexOf($intern_50, qpStart);
            if (end < $intern_7) {
              end = queryParam.length;
            }
            locale = queryParam.substring(qpStart + $intern_49, end);
          }
        }
        if (!locale) {
          locale = __gwt_getMetaProperty($intern_46);
        }
        if (!locale) {
          locale = $wnd[$intern_51];
        }
        if (locale) {
          rtlocale = locale;
        }
        while (locale && !__gwt_isKnownPropertyValue($intern_46, locale)) {
          var lastIndex = locale.lastIndexOf($intern_52);
          if (lastIndex < $intern_7) {
            locale = null;
            break;
          }
          locale = locale.substring($intern_7, lastIndex);
        }
      }
       catch (e) {
        alert($intern_53 + e);
      }
      $wnd[$intern_51] = rtlocale;
      return locale || $intern_47;
    }
    ;
    values[$intern_46] = {'default':$intern_7, 'en':$intern_8, 'ru':$intern_54};
    providers[$intern_55] = function(){
      var ua = navigator.userAgent.toLowerCase();
      var docMode = $doc.documentMode;
      if (function(){
        return ua.indexOf($intern_56) != -1;
      }
      ())
        return $intern_57;
      if (function(){
        return ua.indexOf($intern_58) != -1 && (docMode >= $intern_18 && docMode < $intern_59);
      }
      ())
        return $intern_60;
      if (function(){
        return ua.indexOf($intern_58) != -1 && (docMode >= $intern_61 && docMode < $intern_59);
      }
      ())
        return $intern_62;
      if (function(){
        return ua.indexOf($intern_58) != -1 && (docMode >= $intern_63 && docMode < $intern_59);
      }
      ())
        return $intern_64;
      if (function(){
        return ua.indexOf($intern_65) != -1 || docMode >= $intern_59;
      }
      ())
        return $intern_66;
      return $intern_14;
    }
    ;
    values[$intern_55] = {'gecko1_8':$intern_7, 'ie10':$intern_8, 'ie8':$intern_54, 'ie9':$intern_67, 'safari':$intern_68};
    __gwt_isKnownPropertyValue = function(propName, propValue){
      return propValue in values[propName];
    }
    ;
    MySampleApplication.__getPropMap = function(){
      var result = {};
      for (var key in values) {
        if (values.hasOwnProperty(key)) {
          result[key] = computePropValue(key);
        }
      }
      return result;
    }
    ;
    MySampleApplication.__computePropValue = computePropValue;
    $wnd.__gwt_activeModules[$intern_4].bindings = MySampleApplication.__getPropMap;
    sendStats($intern_0, $intern_69);
    if (isHostedMode()) {
      return computeUrlForResource($intern_70);
    }
    var strongName;
    try {
      unflattenKeylistIntoAnswers([$intern_71, $intern_57], $intern_72);
      unflattenKeylistIntoAnswers([$intern_71, $intern_62], $intern_73);
      unflattenKeylistIntoAnswers([$intern_47, $intern_60], $intern_74);
      unflattenKeylistIntoAnswers([$intern_71, $intern_66], $intern_75);
      unflattenKeylistIntoAnswers([$intern_76, $intern_62], $intern_77);
      unflattenKeylistIntoAnswers([$intern_76, $intern_57], $intern_78);
      unflattenKeylistIntoAnswers([$intern_76, $intern_64], $intern_79);
      unflattenKeylistIntoAnswers([$intern_47, $intern_62], $intern_80);
      unflattenKeylistIntoAnswers([$intern_47, $intern_64], $intern_81);
      unflattenKeylistIntoAnswers([$intern_47, $intern_57], $intern_82);
      unflattenKeylistIntoAnswers([$intern_76, $intern_66], $intern_83);
      unflattenKeylistIntoAnswers([$intern_71, $intern_64], $intern_84);
      unflattenKeylistIntoAnswers([$intern_47, $intern_66], $intern_85);
      unflattenKeylistIntoAnswers([$intern_76, $intern_60], $intern_86);
      unflattenKeylistIntoAnswers([$intern_71, $intern_60], $intern_87);
      strongName = answers[computePropValue($intern_46)][computePropValue($intern_55)];
      var idx = strongName.indexOf($intern_88);
      if (idx != -1) {
        softPermutationId = parseInt(strongName.substring(idx + $intern_8), $intern_18);
        strongName = strongName.substring($intern_7, idx);
      }
    }
     catch (e) {
    }
    MySampleApplication.__softPermutationId = softPermutationId;
    return computeUrlForResource(strongName + $intern_89);
  }

  function loadExternalStylesheets(){
    if (!$wnd.__gwt_stylesLoaded) {
      $wnd.__gwt_stylesLoaded = {};
    }
    sendStats($intern_90, $intern_1);
    sendStats($intern_90, $intern_91);
  }

  processMetas();
  MySampleApplication.__moduleBase = computeScriptBase();
  activeModules[$intern_4].moduleBase = MySampleApplication.__moduleBase;
  var filename = getCompiledCodeFilename();
  if ($wnd) {
    var devModePermitted = !!($wnd.location.protocol == $intern_92 || $wnd.location.protocol == $intern_93);
    $wnd.__gwt_activeModules[$intern_4].canRedirect = devModePermitted;
    function supportsSessionStorage(){
      var key = $intern_94;
      try {
        $wnd.sessionStorage.setItem(key, key);
        $wnd.sessionStorage.removeItem(key);
        return true;
      }
       catch (e) {
        return false;
      }
    }

    if (devModePermitted && supportsSessionStorage()) {
      var devModeKey = $intern_95;
      var devModeUrl = $wnd.sessionStorage[devModeKey];
      if (!/^http:\/\/(localhost|127\.0\.0\.1)(:\d+)?\/.*$/.test(devModeUrl)) {
        if (devModeUrl && (window.console && console.log)) {
          console.log($intern_96 + devModeUrl);
        }
        devModeUrl = $intern_14;
      }
      if (devModeUrl && !$wnd[devModeKey]) {
        $wnd[devModeKey] = true;
        $wnd[devModeKey + $intern_97] = computeScriptBase();
        var devModeScript = $doc.createElement($intern_19);
        devModeScript.src = devModeUrl;
        var head = $doc.getElementsByTagName($intern_98)[$intern_7];
        head.insertBefore(devModeScript, head.firstElementChild || head.children[$intern_7]);
        return false;
      }
    }
  }
  loadExternalStylesheets();
  sendStats($intern_0, $intern_91);
  installScript(filename);
  return true;
}

MySampleApplication.succeeded = MySampleApplication();
