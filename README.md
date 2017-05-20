# NotePad
## 一、功能介绍
      ***此项目是基于google的NotePad进行修改，因此本文只对修改的部分进行介绍***。主要有如下几个方面：
 *    **1、搜索功能**
 *    **2、时间**
 *    **3、背景更改**
 *    **4、UI美化**
 *    **5、分享**
 *    **6、闹钟提醒**
 *    **7、简单画图**
## 二、详细介绍
  *    **1、搜索功能** <br/>
      在搜索框中输入想要查找的笔记的标题，自动匹配符合条件的笔记并显示，主要**通过添加EditText的文本改变的监听器**来实现的，具体**效果**和**代码**如下：<br/>
      <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E6%90%9C%E7%B4%A2.jpg" width="70%" /> <br/>
      
   **首先是搜索的布局,这边使用的控件是EditText**

 ```java    
   
         <EditText
        android:id="@+id/edt"
        android:layout_gravity="center_vertical"
        android:layout_margin="6dp"
        android:drawableLeft="@drawable/search_icon"
        android:drawablePadding="5dp"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:textColor="#000"
        android:background="@drawable/search_edittext_shape"
        android:textSize="16sp"
        android:hint="请输入关键字"/>
        
 ```
   **我们知道，普通EditText样式不会是上图的搜索框那样，因此我们要设置一个shape作为EditText的背景，即这一句：android:background="@drawable/search_edittext_shape"，search_edittext_shape的代码如下**
 ```java    
   
<shape xmlns:android="http://schemas.android.com/apk/res/android">
       <corners android:radius="14dp"/>
       <stroke android:width="2dp" android:color="@color/darkgray"/>
       <solid android:color="@color/color_white"/>
       <padding android:top="8dp" android:bottom="8dp" android:left="5dp"/>
</shape>
 ``` 
 **好了，定义完布局之后，我们就可以在代码中通过添加EditText的文本改变的监听器来模糊搜索我们想要搜索的note，即每当EditText的值发生改变时，我们都获取EditText的值并在数据库中匹配出符合条件的note，并将其显示**
    
  ```java    
    //添加搜索栏editText控件文本变化监听器
        edt.addTextChangedListener(new TextWatcher() {
            private String temp;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                temp = edt.getText().toString();

                Cursor csearch = managedQuery(
                        getIntent().getData(),            // Use the default content URI for the provider.
                        PROJECTION,                       // Return the note ID and title for each note.
                        "title LIKE '%"+temp+"%'",                            // No where clause, return all records.
                        null,                             // No where clause, therefore no where column values.
                        NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
                );

                SimpleCursorAdapter adapter
                        = new SimpleCursorAdapter(
                        NotesList.this,                             // The Context for the ListView
                        R.layout.noteslist_item,          // Points to the XML for a list item
                        csearch,                           // The cursor to get items from
                        dataColumns,
                        viewIDs
                );

                // Sets the ListView's adapter to be the cursor adapter that was just created.
                listview.setAdapter(adapter);

            }
        });
  ```
  *    **2、时间** <br/>
   **在创建或修改note时，通过获取系统时间并更新sqlite中相应note的时间**。具体**效果**和**代码**如下：<br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E4%B8%BB%E7%95%8C%E9%9D%A22.jpg" width="70%" /><br/>
      **Listview中item布局如下：** 

 ```java    
   
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="90dp"
    android:orientation="vertical">

    <ImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:background="@drawable/app_notes"
        android:id="@+id/imageView" />



    <TextView
        android:layout_marginTop="7dp"
        android:id="@+id/txt1"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:text="ascdefg"
        android:textSize="25dp"
        android:singleLine="true"
        android:layout_alignParentTop="true"
        android:layout_toRightOf="@+id/imageView" />
    <TextView
        android:layout_marginTop="45dp"
        android:id="@+id/txt2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="22dp"
        android:text="2017-4-18 14:38"
        android:layout_toRightOf="@+id/imageView" />


</RelativeLayout>
        
 ```
   
   **接下来就是如何调用系统的时间了并设置时间格式（代码已给出注释），当然，这部分代码只在创建note、修改note或修改title时才被调用**

```java    
   
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd   HH:mm:ss");//设置时间格式
        Date curDate =  new Date(System.currentTimeMillis());//获取当前系统时间
        String   time   =   formatter.format(curDate);//将当前时间转换为设置的格式
        values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, time);//插入数据库中
 ```
 
 *    **3、背景更改** <br/>
   **通过选择需要更改的颜色来对listview进行背景更改，并使用SharedPreferences保存更改的颜色**。具体**效果**和**代码**如下：<br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E8%83%8C%E6%99%AF1.jpg" width="70%" /><br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E8%83%8C%E6%99%AF2.jpg" width="70%" /><br/>
   
   **我们知道， SharedPreferences可以用来存储一些简单参数，因此使用SharedPreferences来存储背景最适合不过，所以先创建一个SharedPreferences对象**
 ```java    
   
         private SharedPreferences bac ;
 ```
   **接下来创建一个对话框并添加一些颜色以供选择，代码中的imageview的onClick事件，用于设置背景并将该背景保存至SharedPreferences** 
```java    
   
         AlertDialog.Builder builder=new AlertDialog.Builder(NotesList.this);
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.dia_color,null);
                LinearLayout linearLayout=(LinearLayout) view.findViewById(R.id.dia_linear);
                final int[] colorArray={
                        Color.parseColor("#ffffff"),//白
                        Color.parseColor("#707070"),//黑
                        Color.parseColor("#fcf9a4"),//黄
                        Color.parseColor("#abed65"),//绿
                        Color.parseColor("#fa77ab"),//粉
                        Color.parseColor("#33a9e1"),//蓝
                        Color.parseColor("#070707"),//黑

                };
                for (int i=0;i<7;i++){
                    ImageView imageView=new ImageView(NotesList.this);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(143,180));
                    imageView.setBackgroundColor(colorArray[i]);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {//设置点击事件
                    @Override
                        public void onClick(View v) {
                            NoteAttribute.snoteBackground=colorArray[finalI];
                            listview.setBackgroundColor(NoteAttribute.snoteBackground);
                            listview.setBackgroundColor(colorArray[finalI]);
                            bac
                                    .edit()
                                    .putInt(NoteAttribute.NOTEBACKGROUND,colorArray[finalI])
                                    .apply();
                        }
                    });
                    linearLayout.addView(imageView);
                }
                builder.setView(view).create().show();
        
 ```
 
 *    **4、UI美化** <br/>
   **通过修改控件、背景等对UI界面起到一定程度美化**。具体**效果**如下：<br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E7%BC%96%E8%BE%91.jpg" width="70%" /><br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E4%B8%BB%E7%95%8C%E9%9D%A22.jpg" width="70%" /><br/>
 
   **这里可能有些同学会觉得奇怪，为什么noteList界面是黑色的，而noteEditor却是白色的？其实这边noteEditor设置了theme为白色，而noteList并未设置，只使用系统默认的，因此为了统一风格，我们只需要在AndroidManifest中的application下添加如下代码** 
  ```java    
   
       android:theme="@android:style/Theme.Holo.Light"
        
 ```
    **noteEditor中文本编辑框被美化为蓝色（其实看起来有点丑），也是设置了editext的bacground属性**
  ```java    
   
       <shape xmlns:android="http://schemas.android.com/apk/res/android" >
       <corners android:radius="10px"/>
       <padding android:left="3dip" android:top="3dip" android:right="3dip" android:bottom="3dip"/>
       <stroke
        android:width="2dp"
        android:color="#1E90FF"/>

       </shape>
        
 ```
   
 *    **5、分享** <br/>
   选择某一条note，点击可将**内容分享至其他应用，使用Intent.ACTION_SEND来实现**。具体**效果**和**代码**如下：<br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E5%88%86%E4%BA%AB1.jpg" width="70%" /><br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E5%88%86%E4%BA%AB2.jpg" width="70%" /><br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E5%88%86%E4%BA%AB3.jpg" width="70%" /><br/>
   
   **分享其实算是比较简单，我们设置好点击事件后，通过获取当前选中的note的内容（mCursor.getString(1)即为我们要获取的内容），然后将其分享到我们想要分享的应用** 
   
```java    

       Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
        Cursor mCursor = managedQuery(
                noteUri,         // The URI that gets multiple notes from the provider.
                PROJECTION2,   // A projection that returns the note ID and note content for each note.
                null,         // No "where" clause selection criteria.
                null,         // No "where" clause selection values.
                null          // Use the default sort order (modification date, descending)
        );
   
        Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT, "hi,我是曲兴，来自NotePad的分享："+mCursor.getString(1));
                    startActivity(Intent.createChooser(intent, "分享到"));
        
 ```
 
 *    **6、闹钟提醒** <br/>
   在编辑note界面，可以选择设置闹钟通知**（锁屏情况下也能够通知），通过AlarmManager来实现**。具体**效果**和**代码**如下：<br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E9%97%B9%E9%92%9F1.jpg" width="70%" /><br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E9%97%B9%E9%92%9F2.jpg" width="70%" /><br/>
   **首先需要添加权限**
```java    
    <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
        
```
   **接下来我们定义设置时间的布局（如图一）** 
```java    
  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >
    <Button 
        android:id="@+id/dateButton"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"/>
        <TimePicker 
            android:id="@+id/timePicker"
            android:layout_gravity="center_horizontal"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
        <LinearLayout 
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
            <Button 
                android:id="@+id/positiveButton"
                android:layout_weight="5"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="设定"/>
            <Button 
                android:id="@+id/negativeButton"
                android:layout_weight="5"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="取消"/>
        </LinearLayout>
</LinearLayout>
        
```
   
   **由于业务逻辑较为复杂且代码较多，包括使用TimePicker、Calendar对象设置操作时间，源码已将有关闹钟相关代码放在alert文件夹中。下面我们直接看闹钟的调用**
```java    
       
       private void alertSet(){
        Intent intent = new Intent("android.intent.action.ALARM");
        intent.putExtra("alerttime",alerttime);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteEditor.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),(24 * 60 * 60 * 1000), pendingIntent);
    }
    
 ```
  **当闹钟响起时（图二），可以选择查看进入NotePad或忽略此闹钟**
  ```java    
       
    public void onClick(DialogInterface dialog, int which) {
     // TODO Auto-generated method stub
     switch(which){
     case DialogInterface.BUTTON1:
     {
      Intent intent = new Intent(AlertDialogActivity.this, NotesList.class);
      startActivity(intent);
      finish();
     }
     case DialogInterface.BUTTON2:
     {
      mWakelock.release();
      player.stop();
      finish();
     }
      }
   }
    
 ```
 
 *    **7、简单画图** <br/>
   在编辑note界面中可以选择画图功能，可以保存至系统文件夹，但只支持简单画图。**主要利用Canvas的drawLine（）方法和Android提供的Path（）类，还利用了“双缓冲”技术来保存之前绘制的内容**。具体**效果**如下：<br/>
   <img src="https://github.com/HeyGoing/NotePad-master/blob/master/photos/%E7%94%BB%E5%9B%BE.jpg" width="70%" /><br/>
   **首先创建DisplayMetrics对象并获取宽高**
 ```java    
    DisplayMetrics displayMetrics = new DisplayMetrics();
        // 获取创建的宽度和高度
        getWindowManager().getDefaultDisplay()
                .getRealMetrics(displayMetrics);
        
```
 **初始化baseBitmap对象**
 ```java    
   baseBitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
        
```
 **创建画布并设置背景为灰色**
 ```java    
    // 创建一张画布
        canvas = new Canvas(baseBitmap);
        // 画布背景为灰色
        canvas.drawColor(Color.GRAY);
        
```
 **创建并设置画笔属性**
 ```java    
    paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        // 设置画笔风格
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        // 反锯齿
        paint.setAntiAlias(true);
        paint.setDither(true);
        
```
 **接下来是重点，先将设置Imagview为我们刚刚创建的baseBitmap对象，然后设置Imageview的触摸事件**
 ```java    
    iv.setImageBitmap(baseBitmap);

        iv.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手移动后的坐标
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        // 在开始和结束坐标间画一条线
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        // 实时更新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        iv.setImageBitmap(baseBitmap);
                        break;
                }
                return true;
            }
        });
        
```
 **至此，画图功能完成，我们将其保存至文件夹**
 ```java    
    try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    111+ ".jpg");
            OutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(Environment
                    .getExternalStorageDirectory())));

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        
```

 
   


