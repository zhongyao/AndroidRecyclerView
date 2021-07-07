package com.hongri.recyclerview.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by zhongyao on 2021/6/1
 * Description:
 */
public class MyEditText extends AppCompatEditText {
    private static final String TAG = "MyEditText";

    private OnPasteCallback mOnPasteCallback;

    public MyEditText(Context context) {
        super(context);

        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (mOnPasteCallback != null) {
                    mOnPasteCallback.onEditorAction(v, actionId, event);
                    return true;
                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "afterTextChanged :" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "afterTextChanged---s:" + s + " start:" + start + " before:" + before + " count:" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged---length:" + s.length());
            }
        });

        /**
         * 对输入的文字进行过滤，可以自定义处理
         */
        this.setFilters(new InputFilter[]{new EditPatternFilter("^\\s{0,1}[0-9]{0,1}$")});
    }

    public static class EditPatternFilter implements InputFilter {

        private String patternStr;

        public EditPatternFilter(String patternStr) {
            this.patternStr = patternStr;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            if (TextUtils.isEmpty(source)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(dest.subSequence(0, dstart))
                    .append(source)
                    .append(dest.subSequence(dend, dest.length()));

            Log.d("EditText", "patternStr:" + patternStr + " source:" + source + " start:" + start + " end:" + end + " dest:" + dest + " dtart:" + dstart + " dend:" + dend + " sb:" + sb.toString() + " sb.length:" + sb.length());
            if (!MyEditText.match(patternStr, sb.toString())) {
                return "";
            }

            return source;
        }

    }

    //正则过滤
    public static boolean match(String patternStr, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    public void setOnPasteCallback(OnPasteCallback onPasteCallback) {
        mOnPasteCallback = onPasteCallback;
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        switch (id) {
            case android.R.id.cut:
                // 剪切
                if (mOnPasteCallback != null) {
                    mOnPasteCallback.onCut();
                }
                break;
            case android.R.id.copy:
                // 复制
                if (mOnPasteCallback != null) {
                    mOnPasteCallback.onCopy();
                }
                break;
            case android.R.id.paste:
                // 粘贴
                if (mOnPasteCallback != null) {
                    mOnPasteCallback.onPaste();
                }
        }
        return super.onTextContextMenuItem(id);
    }

    public interface OnPasteCallback {
        void onCut();

        void onCopy();

        void onPaste();

        void onEditorAction(TextView v, int actionId, KeyEvent event);
    }
}
