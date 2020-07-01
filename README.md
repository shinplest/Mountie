# Mountain + Bestie = Mountie
 

## Your Best friend when you hike. 
`Do you wanna hike?`
`Be with me!!`
## Description


Our application is for beginner of hiking. 

4 Main Functions here.

1. **Map**: You can show the hiking trail on the map where you want, and click on the hiking trail to reveal brief information of the hiking trail.

1. **Navigation**: Click on the hiking trail to start the navigation function. If you follow the road, you can check how far you are. Because the whole road is gray and the road to go is mint.

1. **Record**: After climbing, his mountain climbing record will remain in the record view, and he used the Shared Preference to keep the record intact even when the app was closed.

1. **Dashboard**: If the data is stacked on the record, wouldn't it be possible to make a dashboard with that record? You can see the total amount of time you spent climbing mountains and the number of hiking trails you have been hiking every week.

## Demo Images

[[https://github.com/shinplest/Mountie/blob/master/readme_img/wiki1.PNG|alt=octocat]]


[[https://github.com/shinplest/Mountie/blob/master/readme_img/wiki2.PNG|alt=octocat]]


[[https://github.com/shinplest/Mountie/blob/master/readme_img/wiki3.PNG|alt=octocat]]


## Demo Video
[![Video Label](http://img.youtube.com/vi/F67EdiM2oRQ/0.jpg)](https://www.youtube.com/watch?v=F67EdiM2oRQ)



## Key features
- Show all trails in Korea
- Search near mountain trails
- Record when you hike and save it
- Give information about trains(ex, level of difficulty, altitude)


## Progress
- Splash Activity
- Tab bar with viewpager
- Navermap sdk
- Get data with Retrofit from Open API
- Convert Json data to use
- Draw all trails on the map
- Search activity to search mountain with recycler view
  * Store mountain's information (name, latitude and longitude) with arraylist and show mountain's name with recycler view
  * You can search mountain by name and when you click the mountain's name, map goes to that mountain
- Record fragment to save hiking data
- Dashboard fragment to show statistical graph
  * You can see how many hours you climbed by text and how many time you climbed by graph
  * We get data which is stored in shared preference and calculate it to show you 

## Technology
* `Retrofit2` 
Implement Data Model and get **network interaction** with this great Library.
I bet it save our life

* `MVP pattern`
We try to use **MVP design pattern** for being easy to read code and make a clean code.
Would be good habit in development

* `NaverMap open API`
Use NaverMap on **Map** and **Navigation** View. The reason is the latest updated map API and the UI would be familiar with us 

* `Gson`
Gson Libaray use in data parsing from the **raw json data to java class.**

* `LineChart`


## Team Member
신재형
https://github.com/shinplest

유희진
https://github.com/Huijiny

정수경
https://github.com/JeongSuKyoung

전수환
https://github.com/JeonSuHwan
