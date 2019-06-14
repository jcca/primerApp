package com.example.primer;

import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodCall;
import primer.Primer;
import primer.OnIterationDone;

public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    MethodChannel methodChannel = new MethodChannel(getFlutterView(), "go/gonative");
    methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
      @Override
      public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals("dataProcessor_primitive")) {
          if (!methodCall.hasArgument("name") && !methodCall.hasArgument("inters") &&
          !methodCall.hasArgument("size") && !methodCall.hasArgument("workers") && !methodCall.hasArgument("mode")) {
            result.error("dataProcessor_primitive", "Send argument as Map<\"data\", int, ...>", null);
            return;
          }
          try {
            String name = methodCall.argument("name");
            Integer inters = methodCall.argument("inters");
            Integer size = methodCall.argument("size");
            Integer workers = methodCall.argument("workers");
            Integer mode = methodCall.argument("mode");
            Primer.process(name, inters.longValue(), size.longValue(), workers.longValue(), mode.longValue(), new OnIterationDone() {
              @Override
              public void do_(long p) {
                // ... if p == 100 lamar a flutter para cargar la imagen
              }
            });
            result.success("");
            return;
          } catch (Exception e) {
            result.error("dataProcessor_primitive", e.getMessage(), null);
          }
        } else {
          result.notImplemented();
        }
      }
    });
  }
}
