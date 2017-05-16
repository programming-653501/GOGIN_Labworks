package by.bsuir.alarmui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ListItem extends FrameLayout {

	private TextView mTextView;
	private CompoundButton mButton;

	public ListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//Ініцыялізаваць і наладзіць view layout
		RelativeLayout layout = new RelativeLayout(context);
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                         ViewGroup.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(layoutParams);
		
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mButton.toggle();
			}
		});
		
		layout.setBackgroundResource(R.drawable.color_selector);
		
		//Ініцыялізаваць і наладзіць злучэнне mButton

        mButton = new Switch(context);
		mButton.setId(1);
		mButton.setText("");
		
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		buttonParams.topMargin = 16;
		buttonParams.bottomMargin = 16;
		buttonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		buttonParams.addRule(RelativeLayout.ALIGN_RIGHT, 2);


		//Ініцыялізаваць і наладзіць пазнаку кнопкі
		mTextView = new TextView(context);
		mTextView.setId(2);
		
		RelativeLayout.LayoutParams labelParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		labelParams.leftMargin = 8;
		labelParams.addRule(RelativeLayout.ALIGN_BASELINE, 1);
		
		//Пусты погляд
		View emptyView = new View(context);
		RelativeLayout.LayoutParams emptyViewParams = new RelativeLayout.LayoutParams(0, 0);
		emptyViewParams.addRule(RelativeLayout.BELOW, mButton.getId());
		
		//Дадаць кампаненты ў макеце
		layout.addView(mTextView, labelParams);
		layout.addView(mButton, buttonParams);
		layout.addView(emptyView, emptyViewParams);
		addView(layout);
		
		//Кіраванне атрыбутамі
		int[] attributeSet = {
				android.R.attr.text,
				android.R.attr.checked
		};
		
		TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, attributeSet, 0, 0);

		try {
			mTextView.setText(typedArray.getText(0));
			mButton.setChecked(typedArray.getBoolean(1, false));
		} finally {
			typedArray.recycle();
		}
	}

	public void setText(String text) {
		mTextView.setText(text);
	}

	public void setChecked(boolean isChecked) {
		mButton.setChecked(isChecked);
	}

	public boolean isChecked() {
		return mButton.isChecked();
	}

}
