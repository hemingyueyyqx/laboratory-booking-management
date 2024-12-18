# Laboratory Management
[//]: # (设计界面反推功能<br>)
**首页** <br>
加载当前教师课表，授课信息；<br>
上导航（右侧个人中心下拉图标），功能左侧边栏，展示区，节数1234，二维数组，课表中包含，课程信息，实验命名，周次<br>


**功能** <br>
1. 课程管理
加载当前教师全部课程；禁止移除已预约实验室的课程；<br>
table,crud课程,课程列表，图标加号，课程名称，学时等等，点击加按钮弹出模态框，提交按钮，输入限制，不能输入ABC，number类型，右上角×或关闭，返回刷新课表。
2. 预约（二级标题 课程预约，临时预约）<br>
加载全部可用状态实验室，选择实验室，加载实验室详细预约课表，渲染占用课程名称/教师姓名等，当前教师的预约加强渲染。<br>
基于课程预约，列出当前老师所有课程名称（列表形式），加载全部课程，两个接口，当选择课程后，加载详细信息，选课表示，4/8，8/8，需不需要从数据库查；<br>
渲染已经选择次数，以及总次数；（用户课表拿出，首页已经加载课表数据，是否需要重新请求？）同一接口？<br>
label标签，901，902，人数限制，人数不够的实验室列出不让选（还是不列出）<br>
复用首页课表组件，一个格中不一定什么样，可能不止一个，点击空白格，渲染出详细信息，弹窗模态框，选择指定周，1，2，3...复选框，不可选，不渲染<br>
被占用的，点击打开，看我想要的有没有被占用。<br>
3. 加载当前教师全部课程名称，加载选择课程详细信息，显示已预约节数与总节数；首页已基于教师加载全部课程的预约数据，是否有必要基于课程加载？前端缓存？当前预约情况，课程预约记录，后有垃圾桶，移除，没有修改，重新预约。

**查询**<br>
基于学期/周/星期/节，查询可用实验室？<br>
基于学期/周/星期，查询用空闲节的可用教室?<br>
基于appointment_json,如何更新课程名称/用户名称？<br>
表测试记录数量>=6条；否则MySQL会优化查询，全表扫描大于索引。<br>
移除预约？<br>
添加临时预约？<br>
在现有数据表中添加/移除索引等？<br>