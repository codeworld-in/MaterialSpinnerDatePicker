# MaterialSpinnerDatePicker
MaterialSpinnerDatePicker is a date picking library that is built considering the material design standards

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


![Demo Image](https://codeworld.in/wp-content/uploads/2020/05/promo_image_material_date_picker.png)

### Demo
![](https://github.com/codeworld-in/MaterialSpinnerDatePicker/blob/master/Demo.gif)





### Why?

There are already some of the libraries available which does the same thing as this library does but I still built it because I was trying to learn how to build libraries so after building this I just shared it.

### Import Guidelines 
 
 ###### **Step 1 : Add the JitPack repository to your build file**
Add it in your root build.gradle at the end of repositories:
 ```java
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
   ``` 
   
##### **Step 2. Add the dependency**
 ```java
dependencies {
		implementation 'com.github.codeworld-in:MaterialSpinnerDatePicker:1.3'
	}
  ```
  
  ### Usage  
   ```java
MaterialSpinnerDatePicker datePicker = new MaterialSpinnerDatePicker(activity)
                        .setDividerColor(R.color.colorPrimary)
                        .setNextButtonColor(R.color.colorPrimary) // For custom bg color of next button
                        .setNextButtonTextColor(R.color.white) // For custom Text color of next button
                        .setTopBarBGColor(R.color.colorPrimary) // For custom bg color of top bar
                        .setNextButtonText("Next") // For custom text of next button
                        .setYearRange(2000, 2020) // For custom year ranges 
                        .setDefaultDateToToday() // For setting date default to today's date
                        //.setDate(calendar)  For Setting a custom date pass an calender instance Object
                        .setCloseOnTouchOutSide(true) // Set this to true if you want to close dailouge on clicking outside of it
                        .setTitle("Color Demo 1 ") // to set the title 
			     .setTitleTextColor(R.color.colorPrimary) // for customizing text color of the title
                        //.hideTitle() To hide the title text
                        //.hideTopBar() To hide the top bar
                        .setTopBarTextColor(R.color.colorPrimary) // For custom top bar text color
                        .setOnDateSelectedListener(new MaterialSpinnerDatePicker.MaterialDatePickerListener() {
                            @Override
                            public void onDateSelected(String dateString, String rawDateString, Date dateObject) {
                            
                        // Do your tasks by taking the selected date here
                        // dateString will give the text of the date selected i.e Ex : 12 January 2020 
                        // rawDateString will give the date i.e 12-1-2020
                        // dateObject give an instance of the date selected Note : Time of dateObject instance will not be set 
                        
                            }
                        });

                datePicker.show(); // Finally Show the dailouge
```

### Sample APP

Clone this repo and check out the [app](https://github.com/codeworld-in/MaterialSpinnerDatePicker/tree/master/app) module.

### Special Note 

You are free to do anything with this library. #KeepMakingMagic


### Licence 

MIT License

Copyright (c) 2020 CodeWorld.in

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
