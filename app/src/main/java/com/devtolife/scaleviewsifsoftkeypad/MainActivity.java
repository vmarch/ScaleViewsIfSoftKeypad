package com.devtolife.scaleviewsifsoftkeypad;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    float tv1Height;
    float tv1TextSize;
    float tv1PaddingTop;
    float tv1PaddingBottom;

    float imgHeight;
    float imgWidth;
    float imgPaddingTop;
    float imgPaddingBottom;

    float etHeight;
    float tv2Height;

    float fragmentViewHeight;

    float stableViewsHeight;
    float keypadHeightHiden;

    float resizableViewsHeight;
    boolean isChecked = false;

    float keypadHeightSecond;

    float usablePlaceOpen = 0;
    float unusablePlaceOpen;

    float koefic = 0;


    /* Views */
    @BindView(R.id.textView1)
    TextView textView1;

    @BindView(R.id.imageView)
    ImageView img;

    @BindView(R.id.editView)
    EditText editText;

    @BindView(R.id.textView2)
    TextView textView2;

    @BindView(R.id.root_layout)
    LinearLayout relativeLayout;

    LinearLayout.LayoutParams lParamsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        lParamsImg = (LinearLayout.LayoutParams) img.getLayoutParams();

        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                relativeLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = relativeLayout.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                if (!isChecked) {
                    getDataOfHeight();
                    keypadHeightHiden = screenHeight - r.bottom;
                } else {

                    keypadHeightSecond = screenHeight - r.bottom;

                    if (keypadHeightHiden < keypadHeightSecond) {
                        if (usablePlaceOpen == 0) {
                            getUsablePlaceOpened();
                            getKoeficient();
                            askForResize();
                        } else {
                            askForResize();
                        }
                    } else {
                        normalizeViews();
                    }
                }
            }
        });

    }

    private void getDataOfHeight() {

        //getting views parameters

        getDataTvNameAddress();

        getDataImg();

        getDataEditAndTxtViews();

        //getting layoutData
        fragmentViewHeight = relativeLayout.getHeight();

        getStablePlace();

        getHeightResizableViews();

        isChecked = true;
    }

    private void getDataTvNameAddress() {
        tv1TextSize = textView1.getTextSize();
        tv1Height = textView1.getHeight();
        tv1PaddingBottom = textView1.getPaddingBottom();
        tv1PaddingTop = textView1.getPaddingTop();
    }

    private void getDataImg() {
        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
        imgPaddingTop = img.getPaddingTop();
        imgPaddingBottom = img.getPaddingBottom();
    }

    private void getDataEditAndTxtViews() {
        etHeight = editText.getHeight();
        tv2Height = textView2.getHeight();
    }

    private void getStablePlace() {
        stableViewsHeight = etHeight + tv2Height;
    }

    private void getHeightResizableViews() {
        resizableViewsHeight = textView1.getHeight() + img.getHeight();
    }

    private void getUsablePlaceOpened() {
        unusablePlaceOpen = stableViewsHeight + keypadHeightSecond;
        usablePlaceOpen = fragmentViewHeight - unusablePlaceOpen;
    }

    private void getKoeficient() {
        koefic = usablePlaceOpen / resizableViewsHeight;
    }

    private void askForResize() {

        if (resizableViewsHeight > usablePlaceOpen) {
            resizeViews();
        } else {
            normalizeViews();
        }
    }

    private void resizeViews() {

        textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv1TextSize * koefic);
        textView1.setPadding(0, (int) (tv1PaddingTop * koefic), 0, (int) (tv1PaddingBottom * koefic));

        lParamsImg.width = (int) (imgWidth * koefic);
        lParamsImg.height = (int) (imgHeight * koefic);
        img.setPadding(0, 8, 0, 8);

    }

    private void normalizeViews() {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv1TextSize);
        textView1.setPadding(0, (int) tv1PaddingTop, 0, (int) tv1PaddingBottom);

        lParamsImg.width = (int) imgWidth;
        lParamsImg.height = (int) imgHeight;
        img.setPadding(0, (int) imgPaddingTop, 0, (int) imgPaddingBottom);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
