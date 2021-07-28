/*
 * Copyright (C) 2017 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.services.sync.actions.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.opendatakit.logging.WebLogger;
import org.opendatakit.services.R;
import org.opendatakit.services.sync.actions.fragments.LoginFragment;
import org.opendatakit.services.sync.actions.viewModels.LoginViewModel;

/**
 * An activity to provide a simplified login user interface.
 *
 * Created by jbeorse on 5/30/17.
 */

public class LoginActivity extends AbsSyncBaseActivity {

   private static final String TAG = LoginActivity.class.getSimpleName();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      absSyncViewModel=new ViewModelProvider(LoginActivity.this).get(LoginViewModel.class);
      super.onCreate(savedInstanceState);

      navController=Navigation.findNavController(this, R.id.navHostSync);
      navController.setGraph(R.navigation.nav_graph_login);

      getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
         if(event.equals(Lifecycle.Event.ON_RESUME)){
            if(navController.getCurrentDestination()==null){
               navController.navigate(R.id.loginFragment);
            }
         }
      });
   }

   @Override
   public boolean dispatchTouchEvent(MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
         View v = getCurrentFocus();
         if ( v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
               v.clearFocus();
               InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
         }
      }
      return super.dispatchTouchEvent( event );
   }

}
