// Generated by view binder compiler. Do not edit!
package com.example.dashboardscreen.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dashboardscreen.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentFriendDetailsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView amountText;

  @NonNull
  public final Button buttonAddExpense;

  @NonNull
  public final Button buttonSettleUp;

  @NonNull
  public final RecyclerView expensesRv;

  @NonNull
  public final TextView friendName;

  @NonNull
  public final TextView initialsTextView;

  @NonNull
  public final TextView owesText;

  private FragmentFriendDetailsBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView amountText, @NonNull Button buttonAddExpense,
      @NonNull Button buttonSettleUp, @NonNull RecyclerView expensesRv,
      @NonNull TextView friendName, @NonNull TextView initialsTextView,
      @NonNull TextView owesText) {
    this.rootView = rootView;
    this.amountText = amountText;
    this.buttonAddExpense = buttonAddExpense;
    this.buttonSettleUp = buttonSettleUp;
    this.expensesRv = expensesRv;
    this.friendName = friendName;
    this.initialsTextView = initialsTextView;
    this.owesText = owesText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentFriendDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentFriendDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_friend_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentFriendDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.amountText;
      TextView amountText = ViewBindings.findChildViewById(rootView, id);
      if (amountText == null) {
        break missingId;
      }

      id = R.id.buttonAddExpense;
      Button buttonAddExpense = ViewBindings.findChildViewById(rootView, id);
      if (buttonAddExpense == null) {
        break missingId;
      }

      id = R.id.buttonSettleUp;
      Button buttonSettleUp = ViewBindings.findChildViewById(rootView, id);
      if (buttonSettleUp == null) {
        break missingId;
      }

      id = R.id.expensesRv;
      RecyclerView expensesRv = ViewBindings.findChildViewById(rootView, id);
      if (expensesRv == null) {
        break missingId;
      }

      id = R.id.friendName;
      TextView friendName = ViewBindings.findChildViewById(rootView, id);
      if (friendName == null) {
        break missingId;
      }

      id = R.id.initialsTextView;
      TextView initialsTextView = ViewBindings.findChildViewById(rootView, id);
      if (initialsTextView == null) {
        break missingId;
      }

      id = R.id.owesText;
      TextView owesText = ViewBindings.findChildViewById(rootView, id);
      if (owesText == null) {
        break missingId;
      }

      return new FragmentFriendDetailsBinding((ConstraintLayout) rootView, amountText,
          buttonAddExpense, buttonSettleUp, expensesRv, friendName, initialsTextView, owesText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
