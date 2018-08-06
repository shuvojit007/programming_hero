package com.example.test.programmingmama.View.Fragment.SlideFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.App;
import com.example.test.programmingmama.Di.Details_Fragment_Feature.DaggerDetailsFragmentComponent;
import com.example.test.programmingmama.Di.Details_Fragment_Feature.DetailsFragmentComponent;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.Activity.Comments_List;
import com.example.test.programmingmama.View.ViewPager.CommunityMultipleModuleListViewPager;
import com.example.test.programmingmama.View.Activity.Congrats_Pop_Up;
import com.example.test.programmingmama.View.ViewPager.CommunityViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import javax.inject.Inject;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.realm.Realm;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class DetailsFragment extends Fragment {


    LinearLayout run, ptxtlin;
    SwitchCompat premimumswitch;

    TextView ptxt;
    String ptxtstr = "";

    View v;
    TextView des01, des02, des03, output, modulename;
    ImageView img01, img02;
    CodeView codeView;
    Context cn;
    Button btndetails;
    RelativeLayout feedback;

    @Inject
    Picasso picasso;


    Realm realm;
    int id;

    //todo Home
    String finish1 = " ";
    String detailsHmId;
    String detailsStatus = " ";

    //todo Home List
    String detailsListId;
    String finish2 = "";
    String fmodule = "";
    String detailsListStatus = " ";
    int ListId;
    ImageView photoView;
    LikeButton bookmark;
    String BookMarkStatus;
    int i;
    PrefManager prefManager;

    private DatabaseReference mDatabaseReference;

    String strTitle, strDes01, strDes02, strDes03, strImg01, strImg02, strCode, strOut;
    String strPopup = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cn = getContext();
        prefManager = new PrefManager(cn);
        realm = Realm.getDefaultInstance();
        setUpDagger2(cn);
        if (strTitle.equals("take")) {
            v = inflater.inflate(R.layout.frag_details02, container, false);
            init2(v);
        } else if (strTitle.equals("Summary")) {

            v = inflater.inflate(R.layout.frag_details02, container, false);
            modulename = v.findViewById(R.id.modulename);
            modulename.setText(strTitle);
            des01 = v.findViewById(R.id.des01);
            des01.setVisibility(View.GONE);
            photoView = v.findViewById(R.id.photoview);
            photoView.setVisibility(View.VISIBLE);
            Glide.with(cn).load(strImg01).apply(fitCenterTransform()).into(photoView);
            init3(v);
        } else {
            v = inflater.inflate(R.layout.frag_details, container, false);
            init(v);
        }


        if (id == 1) {
            btndetails.setText(" Let's Start ");
        }
        return v;
    }

    private void init3(View v) {
        feedback = v.findViewById(R.id.feedback);
        feedback.setVisibility(View.GONE);
        btndetails = v.findViewById(R.id.btndetails);

        //todo =========Home Module======//

        if (finish1.equals("true")) {
            if (!detailsStatus.equals("true")) {
                btndetails.setText("Finish");
                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeMark(realm, id, detailsHmId, 50);
                    CommunityViewPager.Updatetl();
                    RealmService.UpdateHomeModule(cn, realm, id);


                    if (!strPopup.equals("false") || !strPopup.equals("")) {

                        if (id == 1) {

                            startActivity(new Intent(cn, MainActivity.class));
                            getActivity().finishAffinity();
                        } else {
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 1));
                            prefManager.SetHomePosition(id - 1);
                        }

                    } else {
                        startActivity(new Intent(cn, MainActivity.class));
                    }
                });
            } else {
                btndetails.setText("Finish");
                btndetails.setOnClickListener(vv -> {
                    startActivity(new Intent(cn, MainActivity.class));
                });
            }

        } else if (finish1.equals("false")) {
            if (!detailsStatus.equals("true")) {

                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeMark(realm, id, detailsHmId, 50);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                });
            } else {
                btndetails.setOnClickListener(vv -> CommunityViewPager.RightForward());
            }

        }


        //todo =======HOme List Module========//

        if (finish2.equals("true")) {
            RealmService.UnlockListModule(realm, id, ListId);
            if (fmodule.equals("false")) {
                if (!detailsListStatus.equals("true")) {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        RealmService.HomeListMark(realm, detailsListId, 50); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                        CommunityMultipleModuleListViewPager.Updatetl(); // Add sum to the List

                        if (!strPopup.equals("false") || !strPopup.equals("")) {

                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 0));
                        }
                        getActivity().finish();
                    });
                } else {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        getActivity().finish();
                    });
                }

            } else {
                if (!detailsListStatus.equals("true")) {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        RealmService.HomeListMark(realm, detailsListId, 50); //Add point to list module

                        RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);

                        if (!strPopup.equals("false") || !strPopup.equals("")) {
                            prefManager.SetHomePosition(id - 1);
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 1));
                        } else {
                            getActivity().startActivity(new Intent(cn, MainActivity.class));
                        }

                        //
                    });

                } else {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        getActivity().startActivity(new Intent(cn, MainActivity.class));
                    });

                }

            }
        } else if (finish2.equals("false")) {
            if (!detailsListStatus.equals("true")) {
                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeListMark(realm, detailsListId, 50);
                    RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                });
            } else {
                btndetails.setOnClickListener(vv -> {
                    CommunityMultipleModuleListViewPager.RightForward();
                });
            }

        }
    }

    private void init2(View v) {
        feedback = v.findViewById(R.id.feedback);
        feedback.setVisibility(View.GONE);

        des01 = v.findViewById(R.id.des01);
        des01.setText(Html.fromHtml(strDes01));

        bookmark = v.findViewById(R.id.bookmark);
        bookmark.setEnabled(true);
        bookmark.setAnimationScaleFactor(2);
        if (BookMarkStatus.equals("true")) {
            //   bookmark.setImageResource(R.drawable.heart_fill);
            bookmark.setLiked(true);
        } else {
            //    bookmark.setImageResource(R.drawable.heart_unfill);
            bookmark.setLiked(false);
        }
        bookmark.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                if (BookMarkStatus.equals("true")) {
                    RealmService.UpdateBookmark(realm, id, detailsListId, 1);
                    // bookmark.setImageResource(R.drawable.heart_unfill);
                    bookmark.setLiked(false);
                    BookMarkStatus = "false";
                } else {
                    RealmService.UpdateBookmark(realm, id, detailsListId, 1);
                    // bookmark.setImageResource(R.drawable.heart_fill);
                    bookmark.setLiked(true);
                    BookMarkStatus = "true";
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (BookMarkStatus.equals("true")) {
                    RealmService.UpdateBookmark(realm, id, detailsListId, 1);
                    // bookmark.setImageResource(R.drawable.heart_unfill);
                    bookmark.setLiked(false);
                    BookMarkStatus = "false";
                } else {
                    RealmService.UpdateBookmark(realm, id, detailsListId, 1);
                    // bookmark.setImageResource(R.drawable.heart_fill);
                    bookmark.setLiked(true);
                    BookMarkStatus = "true";
                }
            }
        });


        btndetails = v.findViewById(R.id.btndetails);

        //todo =========Home Module======//

        if (finish1.equals("true")) {
            if (!detailsStatus.equals("true")) {
                btndetails.setText("Finish");
                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeMark(realm, id, detailsHmId, 50);
                    CommunityViewPager.Updatetl();
                    RealmService.UpdateHomeModule(cn, realm, id);


                    if (!strPopup.equals("false") || !strPopup.equals("")) {
                        if (id == 1) {
                            prefManager.SetHomePosition(id - 1);
                            startActivity(new Intent(cn, MainActivity.class));
                            getActivity().finishAffinity();
                        } else {
                            prefManager.SetHomePosition(id - 1);
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 1));
                        }

                    } else {
                        startActivity(new Intent(cn, MainActivity.class));
                    }

                });
            } else {
                btndetails.setText("Finish");
                btndetails.setOnClickListener(vv -> {
                    startActivity(new Intent(cn, MainActivity.class));
                });
            }

        } else if (finish1.equals("false")) {
            if (!detailsStatus.equals("true")) {

                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeMark(realm, id, detailsHmId, 50);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                });
            } else {
                btndetails.setOnClickListener(vv -> CommunityViewPager.RightForward());
            }

        }


        //todo =======HOme List Module========//

        if (finish2.equals("true")) {
            RealmService.UnlockListModule(realm, id, ListId);
            if (fmodule.equals("false")) {
                if (!detailsListStatus.equals("true")) {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        RealmService.HomeListMark(realm, detailsListId, 50); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                        CommunityMultipleModuleListViewPager.Updatetl(); // Add sum to the List

                        if (!strPopup.equals("false") || !strPopup.equals("")) {
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 0));
                        }
                        getActivity().finish();
                    });
                } else {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        getActivity().finish();
                    });
                }

            } else {
                if (!detailsListStatus.equals("true")) {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        RealmService.HomeListMark(realm, detailsListId, 50); //Add point to list module

                        RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);

                        if (!strPopup.equals("false") || !strPopup.equals("")) {

                            if (id == 1) {
                                prefManager.SetHomePosition(id - 1);
                                startActivity(new Intent(cn, MainActivity.class));
                                getActivity().finishAffinity();
                            } else {

                                prefManager.SetHomePosition(id - 1);
                                startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                        .putExtra("popup", strPopup)
                                        .putExtra("value", 1));
                            }

                        } else {
                            startActivity(new Intent(cn, MainActivity.class));
                        }

                        //
                    });

                } else {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        startActivity(new Intent(cn, MainActivity.class));
                    });

                }

            }
        } else if (finish2.equals("false")) {
            if (!detailsListStatus.equals("true")) {
                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeListMark(realm, detailsListId, 50);
                    RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                });
            } else {
                btndetails.setOnClickListener(vv -> {
                    CommunityMultipleModuleListViewPager.RightForward();
                });
            }

        }
    }

    private void init(View v) {

        ptxtlin = v.findViewById(R.id.ptxtlin);
        premimumswitch = v.findViewById(R.id.premimumswitch);
        ptxt = v.findViewById(R.id.ptxt);

        if (!ptxtstr.equals("")) {
            ptxtlin.setVisibility(View.VISIBLE);
            premimumswitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    ptxt.setText(Html.fromHtml(ptxtstr));
                    ptxt.setVisibility(View.VISIBLE);
                } else {
                    ptxt.setVisibility(View.GONE);
                }
            });

          /*
            ptxtlin.setOnClickListener(vv->{
                ptxt.setText(Html.fromHtml(ptxtstr));
                ptxt.setVisibility(View.VISIBLE);
                ptxtlin.setVisibility(View.GONE);
            });*/
        } else {
            ptxtlin.setVisibility(View.GONE);

        }


        //todo Module Question//
        if (i == 0) {
            mDatabaseReference = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Feedback")
                    .child("content")
                    .child(String.valueOf(id));

        } else {
            mDatabaseReference = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Feedback")
                    .child("content")
                    .child(String.valueOf(id))
                    .child(String.valueOf(ListId));

        }


        feedback = v.findViewById(R.id.feedback);
        feedback.setOnClickListener(vv -> {
            new BottomSheet.Builder(cn)
                    .setSheet(R.menu.bottom_details)
                    .setTitle("Feedback or Comment")
                    .setListener(new BottomSheetListener() {
                        @Override
                        public void onSheetShown(BottomSheet bottomSheet, Object o) {

                        }

                        @Override
                        public void onSheetItemSelected(BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {
                            switch (menuItem.getItemId()) {
                                case R.id.feedback:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                                    final View dialogView = getLayoutInflater().inflate(R.layout.content_feedback, null);
                                    EditText ed = dialogView.findViewById(R.id.ed);
                                    Button submit = dialogView.findViewById(R.id.submit);
                                    ImageView cancel = dialogView.findViewById(R.id.cancel);
                                    builder.setView(dialogView);
                                    final AlertDialog dialog = builder.create();
                                    dialog.show();
                                    cancel.setOnClickListener(vv -> dialog.dismiss());
                                    submit.setOnClickListener(v1 -> {
                                        String data = ed.getText().toString();
                                        if (data.equals("") || data.equals(null)) {
                                            Toast.makeText(cn, "Please add valid data", Toast.LENGTH_SHORT).show();
                                        } else {
                                            StoreFeedback(data, dialog);
                                        }
                                    });
                                    break;
                                case R.id.comment:
                                    OpenCmntPopup();
                                    break;

                            }

                        }

                        @Override
                        public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {

                        }
                    })

                    .show();
        });

        //todo Module Question//


        bookmark = v.findViewById(R.id.bookmark);

        bookmark.setAnimationScaleFactor(2);
        if (BookMarkStatus.equals("true")) {
            bookmark.setLiked(true);
        } else {
            bookmark.setLiked(false);
        }


        bookmark.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (BookMarkStatus.equals("true")) {
                    RealmService.UpdateBookmark(realm, id, detailsHmId, 0);
                    BookMarkStatus = "false";
                } else {
                    RealmService.UpdateBookmark(realm, id, detailsHmId, 0);
                    BookMarkStatus = "true";
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (BookMarkStatus.equals("true")) {
                    RealmService.UpdateBookmark(realm, id, detailsHmId, 0);
                    BookMarkStatus = "false";
                } else {
                    RealmService.UpdateBookmark(realm, id, detailsHmId, 0);
                    BookMarkStatus = "true";
                }
            }
        });


        des01 = v.findViewById(R.id.des01);
        des02 = v.findViewById(R.id.des02);
        des03 = v.findViewById(R.id.des03);

        modulename = v.findViewById(R.id.modulename);
        img01 = v.findViewById(R.id.img1);
        img02 = v.findViewById(R.id.img2);
        codeView = v.findViewById(R.id.code_view);


        output = v.findViewById(R.id.output);


        codeView.setVisibility(View.GONE);


        output.setVisibility(View.GONE);
        run = v.findViewById(R.id.run);
        run.setVisibility(View.GONE);


        btndetails = v.findViewById(R.id.btndetails);

        //todo =========Home Module======//

        if (finish1.equals("true")) {
            if (!detailsStatus.equals("true")) {
                btndetails.setText("Finish");
                btndetails.setOnClickListener(vv -> {
                    RealmService.HomeMark(realm, id, detailsHmId, 50);
                    CommunityViewPager.Updatetl();
                    RealmService.UpdateHomeModule(cn, realm, id);
                    if (!strPopup.equals("false") || !strPopup.equals("")) {
                        if (id == 1) {
                            startActivity(new Intent(cn, MainActivity.class));
                            getActivity().finishAffinity();
                        } else {
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 1));
                        }
                    } else {
                        startActivity(new Intent(cn, MainActivity.class));
                    }
                });
            } else {
                btndetails.setText("Finish");
                btndetails.setOnClickListener(vv -> {
                    startActivity(new Intent(cn, MainActivity.class));
                });
            }

        } else if (finish1.equals("false")) {
            if (!detailsStatus.equals("true")) {
                btndetails.setOnClickListener(vv -> {
                    if (!ptxtstr.equals("")) {
                        premimumswitch.setChecked(false);
                        ptxt.setVisibility(View.GONE);
                    }
                    RealmService.HomeMark(realm, id, detailsHmId, 50);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                });
            } else {
                btndetails.setOnClickListener(vv -> CommunityViewPager.RightForward());
            }

        }


        //todo =======HOme List Module========//

        if (finish2.equals("true")) {
            RealmService.UnlockListModule(realm, id, ListId);
            if (fmodule.equals("false")) {
                if (!detailsListStatus.equals("true")) {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        RealmService.HomeListMark(realm, detailsListId, 50); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                        CommunityMultipleModuleListViewPager.Updatetl(); // Add sum to the List
                        if (!strPopup.equals("false") || !strPopup.equals("")) {
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 0));
                        }
                        getActivity().finish();
                    });
                } else {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {

                        getActivity().finish();
                    });
                }

            } else {
                if (!detailsListStatus.equals("true")) {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        RealmService.HomeListMark(realm, detailsListId, 50); //Add point to list module

                        RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);


                        if (!strPopup.equals("false") || !strPopup.equals("")) {
                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                    .putExtra("popup", strPopup)
                                    .putExtra("value", 1));
                        } else {
                            getActivity().startActivity(new Intent(cn, MainActivity.class));
                        }


                    });

                } else {
                    btndetails.setText("Finish");
                    btndetails.setOnClickListener(vv -> {
                        getActivity().startActivity(new Intent(cn, MainActivity.class));
                    });

                }

            }
        } else if (finish2.equals("false")) {
            if (!detailsListStatus.equals("true")) {
                btndetails.setOnClickListener(vv -> {
                    if (!ptxtstr.equals("")) {
                        premimumswitch.setChecked(false);
                        ptxt.setVisibility(View.GONE);
                    }
                    RealmService.HomeListMark(realm, detailsListId, 50);
                    RealmService.ADDLISTMODULEResult(realm, id, ListId); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                });
            } else {
                btndetails.setOnClickListener(vv -> {
                    if (!ptxtstr.equals("")) {
                        premimumswitch.setChecked(false);
                        ptxt.setVisibility(View.GONE);
                    }
                    CommunityMultipleModuleListViewPager.RightForward();
                });
            }

        }

        SetUpTextView();
        SetUpCodeView();
        setUpImageView();

    }

    //todo Module Question//
    private void OpenCmntPopup() {
        if (i == 0) {

            startActivity(new Intent(cn, Comments_List.class)
                    .putExtra("i", i)
                    .putExtra("id", id)
                    .putExtra("name", strTitle));
        } else {

            Log.d("CommentList", " Details ListID : " + ListId);
            startActivity(new Intent(cn, Comments_List.class)
                    .putExtra("i", i)
                    .putExtra("id", id)
                    .putExtra("Listid", ListId)
                    .putExtra("name", strTitle));
        }

    }

    private void StoreFeedback(String data, AlertDialog dialog) {
        HashMap map = new HashMap();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            map.put("feed", data);
            map.put("date", ServerValue.TIMESTAMP);
            map.put("userid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        } else {
            map.put("userid", "anonymous");
            map.put("feed", data);
            map.put("date", ServerValue.TIMESTAMP);
        }


        String id = mDatabaseReference.push().getKey();
        mDatabaseReference.child(id).setValue(map, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                Toast.makeText(cn, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                Toast.makeText(cn, "Thank you for your valuable feedback ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //todo Module Question//


    private void setUpImageView() {
        if (strImg01.equals("null")) {
            img01.setVisibility(View.GONE);
        } else {
            // Picasso.get().load(strImg01).into(img01);
            Glide.with(cn).load(strImg01).apply(fitCenterTransform()).into(img01);
        }

        if (strImg02.equals("null")) {
            img02.setVisibility(View.GONE);
        } else {

            Glide.with(cn).load(strImg02).apply(fitCenterTransform()).into(img02);
            //Picasso.get().load(strImg02).into(img02);
        }
    }

    private void SetUpCodeView() {
        if (strCode.equals("code block")) {

            codeView.setVisibility(View.GONE);
        } else {
            Log.d("Code", "SetUpCodeView: " + strCode);

            codeView.setVisibility(View.VISIBLE);
            codeView.setOptions(Options.Default.get(cn)
                    .withLanguage("python")
                    .withCode(strCode)
                    .withShadows()
                    .withTheme(ColorTheme.MONOKAI));

        }
        if (strOut.equals("null")) {

            output.setVisibility(View.GONE);
            run.setVisibility(View.GONE);
        } else {
            run.setVisibility(View.VISIBLE);
            run.setOnClickListener(v12 -> {
                output.setVisibility(View.VISIBLE);
                output.setText(Html.fromHtml(strOut));
            });

        }

    }


    //todo==========Set Up Dagger 2 =================//
    public void setUpDagger2(Context cn) {

        DetailsFragmentComponent detailsFragmentComponent =
                DaggerDetailsFragmentComponent
                        .builder()
                        .appComponent(App.get((Activity) cn).getAppComponent())
                        .build();

        detailsFragmentComponent.injectDetails(DetailsFragment.this);

    }
    //todo==========Set Up Dagger 2 =================//

    private void SetUpTextView() {
        modulename.setText(strTitle);
        //   Typeface tp = Typeface.createFromAsset(getActivity().getAssets(), "roboto.ttf");


        if (strDes01.equals("null"))
            des01.setVisibility(View.GONE);
        else {
            //  des01.setTypeface(tp);
            des01.setText(Html.fromHtml(strDes01));

        }

        if (strDes02.equals("null")) {
            des02.setVisibility(View.GONE);
        } else {
            //  des02.setTypeface(tp);
            des02.setText(Html.fromHtml(strDes02));

        }
        if (strDes03.equals("null")) {
            des03.setVisibility(View.GONE);
        } else {
            //   des03.setTypeface(tp);
            des03.setText(Html.fromHtml(strDes03));

        }
    }
    // your boolean field


    public void setItem(String title, int id, String des01, String des02, String des03, String link01, String link02, String code, String output, String finish, String detailsStatus, String detailsId, String BookMarkStatus, String popup, String ptxt) {
        strDes01 = des01;
        strDes02 = des02;
        strDes03 = des03;
        strImg01 = link01;
        strImg02 = link02;
        strCode = code;
        strOut = output;
        strTitle = title;
        this.finish1 = finish;
        this.id = id;
        this.detailsHmId = detailsId;
        this.detailsStatus = detailsStatus;
        this.BookMarkStatus = BookMarkStatus;
        i = 0;


        this.strPopup = popup;

        this.ptxtstr = ptxt;
    }


    public void setItem2(String title, int id, String des01, String des02, String des03, String link01, String link02, String code, String output, String finish, String fmodule, String detailsListId, String detailsListStatus, int ListId, String BookMarkStatus, String popup, String ptxt) {
        this.id = id;
        strDes01 = des01;
        strDes02 = des02;
        strDes03 = des03;
        strImg01 = link01;
        strImg02 = link02;
        strCode = code;
        strOut = output;
        strTitle = title;
        this.finish2 = finish;
        this.fmodule = fmodule;
        this.detailsListId = detailsListId;
        this.detailsListStatus = detailsListStatus;
        this.ListId = ListId;
        this.BookMarkStatus = BookMarkStatus;
        i = 1;

        this.strPopup = popup;
        this.ptxtstr = ptxt;

    }


}
