// function ToastyPlugin() {}

// ToastyPlugin.prototype.show = function (message, duration, successCallback, errorCallback) {
//   var options = {};
//   options.message = message;
//   options.duration = duration;

//   cordova.exec(successCallback, errorCallback, "ToastyPlugin", "show", [options]);
// };

// function show (message, duration, successCallback, errorCallback) {
//   var options = {};
//   options.message = message;
//   options.duration = duration;

//   cordova.exec(successCallback, errorCallback, "ToastyPlugin", "show", [options]);
// }

var ToastyPlugin = {
  show: function (message, duration, successCallback, errorCallback) {
    var options = {};
    options.message = message;
    options.duration = duration;

    cordova.exec(successCallback, errorCallback, "ToastyPlugin", "show", [options]);
  },
};

// ToastyPlugin.install = function () {
//   if (!window.plugins) {
//     window.plugins = {};
//   }
//   window.plugins.toastyPlugin = new ToastyPlugin();
//   return window.plugins.toastyPlugin;
// };

// cordova.addConstructor(ToastyPlugin.install);

cordova.addConstructor(function () {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.toastyPlugin = ToastyPlugin;
  return window.plugins.toastyPlugin;
});
