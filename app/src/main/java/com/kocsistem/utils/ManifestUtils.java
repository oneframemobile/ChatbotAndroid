package com.kocsistem.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public final class ManifestUtils {

  public static @Nullable String getMetaData(Context context, String name) {
    try {
      ApplicationInfo ai =
          context
              .getPackageManager()
              .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      Bundle bundle = ai.metaData;

      return bundle.getString(name);
    } catch (PackageManager.NameNotFoundException e) {
      Log.w("Metadata", "Unable to load meta-data: " + e.getMessage());
    }

    return null;
  }
}
