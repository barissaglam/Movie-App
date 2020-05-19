package barissaglam.client.movieapp.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import org.jetbrains.annotations.NonNls;

@NonNls
public class ThemeUtil {

    public static int pxToDp(Context context, int pixel) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return pixel < 0 ? pixel : Math.round(pixel / displayMetrics.density);
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
    }

    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int pxToSp(Context context, int pixel) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return pixel < 0 ? pixel : Math.round(pixel / displayMetrics.scaledDensity);
    }

    public static int spToPx(Context context, int sp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = getAndroidIdentifier(context, "dimen", "status_bar_height");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        //Logger.v("statusBar height: " + result);
        return result;
    }

    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = getAndroidIdentifier(context, "dimen", "navigation_bar_height");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static int getActionBarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float actionBar = TypedValue.complexToDimension(typedValue.data, displayMetrics);
            actionBarHeight = Math.round(actionBar);
        }

        return actionBarHeight;
    }

    public static int getDimenIdentifier(Context context, String name) {
        return getIdentifier(context, "dimen", name);
    }

    public static int getStringIdentifier(Context context, String name) {
        return getIdentifier(context, "string", name);
    }

    public static int getIdentifier(Context context, @NonNls String defType, String name) {
        if (TextUtils.isEmpty(name)) {
            return 0;
        }
        return context.getResources().getIdentifier(name.toLowerCase(), defType, context.getPackageName());
    }

    public static int getAndroidIdentifier(Context context, @NonNls String defType, @NonNls String name) {
        if (TextUtils.isEmpty(name)) {
            return 0;
        }
        return context.getResources().getIdentifier(name.toLowerCase(), defType, "android");
    }
}
