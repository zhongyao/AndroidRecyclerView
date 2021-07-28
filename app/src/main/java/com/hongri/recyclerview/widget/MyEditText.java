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

    /**
     * 注意如下文本输入前后 mBeforeS 及 mBeforeString 的差异性
     */
    private CharSequence mBeforeS;
    private String mBeforeString;

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

            /**
             *
             * @param s 修改前的字符串
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged :" + s + " start:" + start + " count:" + count + " after:" + after);
                mBeforeS = s;
                mBeforeString = s.toString();
                Log.d(TAG, "beforeTextChanged : mBeforeS:" + mBeforeS + " mBeforeString:" + mBeforeString);
            }

            /**
             *
             * @param s 改变后的字符串
             * @param start 有变动的字符串index
             * @param before 被改变的字符串长度，如果是新增则为0
             * @param count 添加的字符串长度，如果是删除则为0
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged---s:" + s + " start:" + start + " before:" + before + " count:" + count);

                if (count != 0) {
                    //表示有新添加字符串
                    Log.d(TAG, "onTextChanged---新输入的子内容：" + s.subSequence(start, start + count));
                } else if (before != 0) {
                    //表示有新删除字符串
                    if (mBeforeString != null && mBeforeString.length() > 0) {
                        Log.d(TAG, "mBeforeS:" + mBeforeS + " mBeforeString:" + mBeforeString);
                        Log.d(TAG, "onTextChanged---新删除的子内容：" + mBeforeString.subSequence(start, start + before));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged---length:" + s.length());
            }
        });

        /**
         * 对输入的文字进行过滤，可以自定义处理
         */
//        this.setFilters(new InputFilter[]{new EditPatternFilter("^\\s{0,1}[0-9]{0,1}$")});
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
