function ToastyPlugin() {}

ToastyPlugin.prototype.checkMock = function (successCallback, errorCallback) {
  // cordova.exec(successCallback, errorCallback, "ToastyPlugin", "check", [options]);
  cordova.exec(successCallback, errorCallback, "ToastyPlugin", "checkMock", []);
};

ToastyPlugin.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.toastyPlugin = new ToastyPlugin();
  return window.plugins.toastyPlugin;
};

cordova.addConstructor(ToastyPlugin.install);
