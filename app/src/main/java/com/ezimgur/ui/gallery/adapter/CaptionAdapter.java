package com.ezimgur.ui.gallery.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ezimgur.R;
import com.ezimgur.datacontract.Comment;
import com.ezimgur.datacontract.GalleryItem;
import com.ezimgur.datacontract.VoteType;
import com.ezimgur.ui.base.UiBuilder;
import com.ezimgur.ui.utils.RichTextUtils;
import com.ezimgur.ui.utils.URLSpanConverter;
import com.ezimgur.ui.utils.VoteUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * EggmanProjects
 * Author: Matthew Harris
 * Date: 10/3/12
 * Time: 9:31 PM
 */
public class CaptionAdapter extends BaseAdapter {

    private GalleryItem item;
    private List<Comment> mCaptions;
    private FragmentManager mFragmentManager;
    private static final String TAG = "EzImgur.CaptionAdapter";

    public CaptionAdapter(GalleryItem item, List<Comment> captions, FragmentManager fragmentManager) {
        mCaptions = captions;
        this.item = item;
        mFragmentManager = fragmentManager;
    }

    public void setCaptions(GalleryItem item, List<Comment> captions) {
        mCaptions = captions;
        this.item = item;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCaptions.size() + 1 ;
    }

    @Override
    public Comment getItem(int i) {
        return mCaptions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView,final ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (i == 0) {
            View headerView = UiBuilder.inflate(viewGroup.getContext(), R.layout.view_header_image_details);

            TextView tvAuthor = (TextView) headerView.findViewById(R.id.view_header_image_details_tv_author);
            TextView tvTimeAgo = (TextView) headerView.findViewById(R.id.view_header_image_details_tv_time_ago);
            TextView tvPoints = (TextView) headerView.findViewById(R.id.view_header_image_details_tv_points);

            tvTimeAgo.setText(DateUtils.getRelativeTimeSpanString(item.dateCreated.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
            tvPoints.setText(item.score + " points");
            if (item.accountUrl != null) {
                tvAuthor.setText("by " + item.accountUrl);
            }
            return headerView;
        }

        i -= 1;

        if (convertView == null || convertView.getTag() == null) {
            convertView = UiBuilder.inflate(viewGroup.getContext(), R.layout.view_caption);

            viewHolder = new ViewHolder();
            viewHolder.txtCaption = (TextView) convertView.findViewById(R.id.cv_caption);
            viewHolder.txtPoints = (TextView) convertView.findViewById(R.id.cv_caption_points);
            viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.cv_author);
            viewHolder.imgMore = (ImageView) convertView.findViewById(R.id.cv_imgViewReplies);
            viewHolder.txtDownVote = (ImageView) convertView.findViewById(R.id.cv_tv_downvote);
            viewHolder.txtUpVote = (ImageView) convertView.findViewById(R.id.cv_tv_upvote);
            viewHolder.txtTimeAgo = (TextView) convertView.findViewById(R.id.cv_timeago);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Comment targetCaption = mCaptions.get(i);
        viewHolder.txtPoints.setText(""+targetCaption.points);
        viewHolder.txtAuthor.setText(targetCaption.author);
        viewHolder.txtAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] items = new CharSequence[1];
                items[0] = "Send Message";

                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                AlertDialog dialog = builder
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(viewGroup.getContext(), CommunityActivity.class);
//                                intent.putExtra(CommunityActivity.EXTRA_COMPOSE_TO, targetCaption.author);
//                                viewGroup.getContext().startActivity(intent);
                            }
                        }).create();
                dialog.show();
            }
        });

        SpannableString spanCaption = new SpannableString(targetCaption.comment);
        viewHolder.txtCaption.setText(spanCaption);
        viewHolder.txtCaption.setText(RichTextUtils.replaceAll((Spanned) viewHolder.txtCaption.getText(), URLSpan.class, new URLSpanConverter()));
        viewHolder.txtTimeAgo.setText(DateUtils.getRelativeTimeSpanString(targetCaption.dateCreated.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));

        updateVotesView(viewHolder, targetCaption, VoteUtils.getVoteTypeFromString(targetCaption.vote));

        final View view = convertView;
        if (targetCaption.children != null && targetCaption.children.size() > 0){
            viewHolder.imgMore.setVisibility(View.VISIBLE);
            viewHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Dialog moreComments = new Dialog(view.getContext(), R.style.Theme_ezimgur_Dialog);
//                    moreComments.setContentView(R.layout.dialog_captions);
//                    moreComments.setTitle("Replies to " + targetCaption.author);
//
//                    WindowManager.LayoutParams lp = moreComments.getWindow().getAttributes();
//                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                    moreComments.getWindow().setAttributes(lp);
//
//                    moreComments.setCanceledOnTouchOutside(true);
////
////                    CaptionGallery replyCaptions = (CaptionGallery) moreComments.findViewById(R.id.dialogCaptionList);
////                    replyCaptions.setCaptions(mGalleryItemId, targetCaption.children, mFragmentManager);
////                    replyCaptions.setAdapter(new CaptionAdapter(mGalleryItemId, targetCaption.children, mFragmentManager));
//
//                    moreComments.show();
                }
            });

        } else {
            viewHolder.imgMore.setVisibility(View.INVISIBLE);
            viewHolder.imgMore.setOnClickListener(null);
        }

        viewHolder.txtUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //dont do anything if not auth
//                if (!EzApplication.isAuthenticatedWithWarning(viewHolder.txtDownVote.getContext()))
//                    return;
//
//                new VoteOnCommentTask(v.getContext(), targetCaption.id, VoteType.UP){
//
//                    @Override
//                    protected void onSuccess(Boolean result) throws Exception {
//                        super.onSuccess(result);
//                        VoteType vote = VoteUtils.getVoteTypeFromString(targetCaption.vote);
//                        if (vote == VoteType.UP) {
//                            targetCaption.points -= 1;
//                            updateVotesView(viewHolder, targetCaption, VoteType.NONE);
//                        } else {
//                            targetCaption.points += 1;
//                            updateVotesView(viewHolder, targetCaption, VoteType.UP);
//                        }
//
//                    }
//                }.execute();
            }
        });

        viewHolder.txtDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //dont do anything if not auth
//                if (!EzApplication.isAuthenticatedWithWarning(viewHolder.txtDownVote.getContext()))
//                    return;
//
//                new VoteOnCommentTask(v.getContext(), targetCaption.id, VoteType.DOWN){
//                    @Override
//                    protected void onSuccess(Boolean result) throws Exception {
//                        super.onSuccess(result);
//                        VoteType vote = VoteUtils.getVoteTypeFromString(targetCaption.vote);
//                        if (vote == VoteType.DOWN) {
//                            targetCaption.points += 1;
//                            updateVotesView(viewHolder, targetCaption, VoteType.NONE);
//                        } else {
//                            targetCaption.points -= 1;
//                            updateVotesView(viewHolder, targetCaption, VoteType.DOWN);
//                        }
//                    }
//                }.execute();
            }
        });




        return convertView;
    }

    private void updateVotesView(ViewHolder viewHolder, Comment targetComment, VoteType vote) {
        Context context = viewHolder.txtDownVote.getContext();

//        switch (vote){
//            case UP:
//                viewHolder.txtUpVote.setImageResource(R.drawable.upvote);
//                viewHolder.txtDownVote.setImageResource(R.drawable.downvote_empty);
//                targetComment.vote = VoteUtils.getUpVoteString();
//                break;
//            case DOWN:
//                viewHolder.txtUpVote.setImageResource(R.drawable.upvote_empty);
//                viewHolder.txtDownVote.setImageResource(R.drawable.downvote);
//                targetComment.vote = VoteUtils.getDownVoteString();
//                break;
//            default:
//                viewHolder.txtUpVote.setImageResource(R.drawable.upvote_empty);
//                viewHolder.txtDownVote.setImageResource(R.drawable.downvote_empty);
//                targetComment.vote = "";
//        }

        viewHolder.txtPoints.setText("" + targetComment.points);

    }

    static class ViewHolder {
        TextView txtCaption;
        TextView txtPoints;
        TextView txtAuthor;
        ImageView imgMore;
        ImageView txtUpVote;
        ImageView txtDownVote;
        TextView txtTimeAgo;
    }
}