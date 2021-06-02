package com.hongri.recyclerview.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Create by zhongyao on 2021/6/1
 * Description:
 */
public class MyEditText extends AppCompatEditText {

    private OnPasteCallback mOnPasteCallback;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
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
    }
}
