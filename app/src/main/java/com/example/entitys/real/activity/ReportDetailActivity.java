package com.example.entitys.real.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.entitys.real.R;
import com.example.entitys.real.fragment.ReportFragment;

public class ReportDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        Intent intent = getIntent();

        //ArrayList<Subjects> DataList = (ArrayList<Subjects>)intent.getSerializableExtra("DataList");
        int groupposition = intent.getIntExtra("groupPosition", 0);
        int childposition = intent.getIntExtra("childPosition", 0);

        TextView title = (TextView)findViewById(R.id.report_title);
        TextView submit = (TextView)findViewById(R.id.report_submit);
        TextView post = (TextView)findViewById(R.id.report_post);
        TextView daed = (TextView)findViewById(R.id.report_dead);
        TextView late = (TextView)findViewById(R.id.report_late);
        TextView content = (TextView)findViewById(R.id.report_content);

        title.setText  ("제목 : "+ReportActivity.DataList.get(groupposition).reportgroup.get(childposition).reportdetail.get(0));
        submit.setText ("제출방식 : "+ReportActivity.DataList.get(groupposition).reportgroup.get(childposition).reportdetail.get(1));
        post.setText   ("게시일 : "+ReportActivity.DataList.get(groupposition).reportgroup.get(childposition).reportdetail.get(2));
        daed.setText   ("마감일 : "+ReportActivity.DataList.get(groupposition).reportgroup.get(childposition).reportdetail.get(3));
        late.setText   ("지각제출 : "+ReportActivity.DataList.get(groupposition).reportgroup.get(childposition).reportdetail.get(4));
        content.setText("내용 : "+ReportActivity.DataList.get(groupposition).reportgroup.get(childposition).reportdetail.get(5));
    }
}
