/*
 * Copyright 2012 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kevinsawicki.wishlist;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Helper for finding and tweaking a view's children
 */
public class ViewFinder {

  private static interface FindWrapper {

    View findViewById(int id);

    Resources getResources();
  }

  private static class WindowWrapper implements FindWrapper {

    private final Window window;

    WindowWrapper(final Window window) {
      this.window = window;
    }

    public View findViewById(final int id) {
      return window.findViewById(id);
    }

    public Resources getResources() {
      return window.getContext().getResources();
    }
  }

  private static class ViewWrapper implements FindWrapper {

    private final View view;

    ViewWrapper(final View view) {
      this.view = view;
    }

    public View findViewById(final int id) {
      return view.findViewById(id);
    }

    public Resources getResources() {
      return view.getResources();
    }
  }

  private final FindWrapper wrapper;

  /**
   * Create finder wrapping given view
   *
   * @param view
   */
  public ViewFinder(final View view) {
    wrapper = new ViewWrapper(view);
  }

  /**
   * Create finder wrapping given window
   *
   * @param window
   */
  public ViewFinder(final Window window) {
    wrapper = new WindowWrapper(window);
  }

  /**
   * Create finder wrapping given activity
   *
   * @param activity
   */
  public ViewFinder(final Activity activity) {
    this(activity.getWindow());
  }

  /**
   * Find view with id
   *
   * @param id
   * @return found view
   */
  @SuppressWarnings("unchecked")
  public <V extends View> V find(final int id) {
    return (V) wrapper.findViewById(id);
  }

  /**
   * Get image view with id
   *
   * @param id
   * @return image view
   */
  public ImageView imageView(final int id) {
    return (ImageView) find(id);
  }

  /**
   * Set text of child view with given id
   *
   * @param id
   * @param content
   * @return text view
   */
  public TextView setText(final int id, final String content) {
    final TextView text = find(id);
    text.setText(content);
    return text;
  }

  /**
   * Set text of child view with given id
   *
   * @param id
   * @param content
   * @return text view
   */
  public TextView setText(final int id, final int content) {
    return setText(id, wrapper.getResources().getString(content));
  }

  /**
   * Register on click listener to child view with given id
   *
   * @param id
   * @param listener
   * @return view registered with listener
   */
  public View onClick(final int id, final OnClickListener listener) {
    View clickable = find(id);
    clickable.setOnClickListener(listener);
    return clickable;
  }

  /**
   * Register runnable to be invoked when child view with given id is clicked
   *
   * @param id
   * @param runnable
   * @return view registered with runnable
   */
  public View onClick(final int id, final Runnable runnable) {
    return onClick(id, new OnClickListener() {

      public void onClick(View v) {
        runnable.run();
      }
    });
  }

  /**
   * Set drawable on child image view
   *
   * @param id
   * @param drawable
   * @return image view
   */
  public ImageView setDrawable(final int id, final int drawable) {
    ImageView image = imageView(id);
    image.setImageDrawable(image.getResources().getDrawable(drawable));
    return image;
  }
}
