<template>
  <div>
    <Header class="header"></Header>
    <el-row :gutter="10">
      <el-col :xs="{span:20,offset:2}" :sm="{span:16,offset:4}" :md="{span:16,offset:4}">
        <div class="tasks-container" :key="refresh">
          <el-card class="box-card" v-for="(item,id) in tasks" :key="'taskId-'+item.task_id" ref="boxCards">
            <div class="task-item">
              <div class="finishButton-box">
                <el-button size="mini" class="finishButton" circle @mouseover.native="hoverButton(id)"
                           @mouseleave.native="leaveButton(id)" @click="changeTaskStatus(item.task_id, item.task_type)">
                  <i class="el-icon-check" ref="icons" style="color: white"></i>
                </el-button>
              </div>
              <div class="task-content" ref="taskContent">
                {{ item.task_content }}
              </div>
              <div ref="taskInput" style="display: none" class="task-content">
                <el-input suffix-icon="el-icon-edit" ref="task_input" type="textarea"  maxlength="120"
                          show-word-limit autosize v-model="taskText" @blur="submitChange(item.task_id, id)" @keydown.enter.native="submitChange(item.task_id, id)"  ></el-input>
                <!--            <el-button @click="submitChange(item.task_id, id)" class="changeSubmitButton" type="primary" icon="el-icon-check" circle></el-button>-->
              </div>
              <div class="moreButton-box">
                <el-dropdown class="moreButton">
                          <span class="el-dropdown-link">
                            <i class="el-icon-more"></i>
                          </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>创建 <i class="el-icon-date" style="color: #409EFF"></i>{{ item.task_createtime }}
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="changeTask(item.task_id, id)" divided>
                    修改 <i class="el-icon-edit" style="color: dodgerblue"></i>
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="deleteTask(item.task_id)" divided>
                    删除 <i class="el-icon-delete" style="color: red"></i>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
              </div>
              <!--<el-link icon="el-icon-more" class="moreButton"  :underline="false"></el-link>-->
            </div>
          </el-card>
          <div class="add-task-box" v-if="!showAddTask">
            <el-input v-model="new_task" type="textarea"
                      maxlength="120"
                      show-word-limit
                      autosize
                      placeholder="请输入内容"></el-input>
            <el-button @click="addTask" size="mini" style="margin-top: 5px"><i class="el-icon-s-flag"></i> 添加</el-button>
            <el-button size="mini" style="margin-top: 5px" @click="addTaskButtonEvt"><i class="el-icon-close"
                                                                                         style="color: red"></i> 取消
            </el-button>
          </div>
          <div class="button-box">
            <el-button class="finishedButton" v-if="showAddTask" size="mini" @click="loadFinishedTasks"><i
                    class="el-icon-arrow-right" ref="finishedButtonIcon"></i> 已完成
              <span style="color:#ACB0AE">
                    {{ finishedTasksCount }}
                </span>
            </el-button>
            <el-button class="finishedButton" v-if="showAddTask" size="mini" @click="addTaskButtonEvt"><i
                    class="el-icon-s-flag"></i> 添加任务
            </el-button>
          </div>

          <el-card class="box-card" v-show="showFinished" v-for="(item, id) in finishedTasks" :key="item.task_id+'-only'">
            <div class="task-item">
              <div class="finishButton-box">
                <el-button size="mini" class="finishButton" circle @click="changeTaskStatus(item.task_id, item.task_type)">
                  <i class="el-icon-check" style="color: green"></i>
                </el-button>
              </div>
              <div class="task-content" style="text-decoration: line-through;">
                {{ item.task_content }}
              </div>
              <div class="moreButton-box">
                <el-dropdown class="moreButton">
                          <span class="el-dropdown-link">
                            <i class="el-icon-more"></i>
                          </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>创建 <i class="el-icon-date" style="color: #409EFF"></i>{{ item.task_createtime }}
                    </el-dropdown-item>
                    <el-dropdown-item>完成 <i class="el-icon-date" style="color: green"></i>{{ item.task_finishtime }}
                    </el-dropdown-item>
                    <el-dropdown-item @click.native="changeTaskStatus(item.task_id, item.task_type)" divided>
                      还原为未完成 <i class="el-icon-refresh" style="color: chartreuse"></i>
                    </el-dropdown-item>
                    <el-dropdown-item @click.native="deleteTask(item.task_id)" divided>
                      删除 <i class="el-icon-delete" style="color: red"></i>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <el-backtop></el-backtop>
  </div>
</template>

<script>
import Header from "../components/Header";

export default {
  name: "Tasks",
  components: {Header},
  data() {
    return {
      tasksCount: 0,
      refresh: -1,
      finishedTasksCount: 0,
      showFinished: false,
      tasks: {},
      finishedTasks: {},
      boxCardsWidth: 0,
      new_task: "",
      taskText:"nihao",
      showAddTask: true,
      expressions: [
        '🎉', '🍜', '✨', '😀', '🍕'
      ]
    }
  },
  mounted() {
    if (!this.$store.state.user.token) {
      this.$router.push("/login")
    } else {
      window.addEventListener('scroll', this.handleScroll, true);
      this.boxCardsWidth = 0;
    }
  },
  created() {
    this.$store.commit("SET_TOKEN", localStorage.getItem("userToken"));
    this.$store.commit("SET_FILTER", localStorage.getItem("filterPattern"));
    if (!this.$store.state.user.token) {
      this.$router.push("/login")
    } else {
      this.$store.commit("LOGIN");
      this.loadAllTasks();
    }
  },
  beforeUpdate() {
    if (!this.$store.state.isLogin) {
      this.tasks = {};
      alert("请登录后再试");
      this.$router.push({name: "Login"});
    }
  },
  updated() {
    if (this.$refs.boxCards !== undefined) {
      this.boxCardsWidth = this.$refs.boxCards['0'].$el.clientWidth;
    }
  },
  methods: {
    hoverButton(index) {
      this.$refs.icons[index].style.color = "green";
    },
    leaveButton(index) {
      this.$refs.icons[index].style.color = "white";
    },
    changeTaskStatus(task_id, task_type) { //status=0，代表对已完成任务操作，1为对未完成任务操作

      let msg = '是否确认完成该任务?';
      if (task_type === 1) { // 让用户再确认一下
        msg = '是否确认还原为未完成?';
      }
      this.$confirm(msg, '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        let param = new FormData;
        param.append("task_id", task_id);
        param.append("task_type", task_type);
        param.append("token", this.$store.state.user.token);
        this.$axios.post("/bc208/api/change", param).then(res => {
          this.loadAllTasks();
          let msg = '完成任务  ' + this.expressions[Math.floor((Math.random() * 5))] + '  累计完成任务 ' + (this.finishedTasksCount + 1);
          if (task_type === 1) {
            msg = "还原成功";
          }
          this.$message({
            type: 'success',
            message: msg
          });
        })
      }).catch(() => {

      });
    },
    addTask() {
      let param = new FormData;
      param.append("token", this.$store.state.user.token);
      param.append("task_content", this.new_task);
      console.log(this.new_task)
      console.log(this.new_task.trim())
      this.$axios.post("/bc208/api/insert", param).then(res => {
        this.loadUnfinishedTasks();
        this.$message({
          type: 'success',
          message: '添加成功'
        });
        this.showAddTask = true
        this.new_task = ""
      })
    },
    loadAllTasks() {
      let pattern = this.$store.state.filterPattern;
      let param = new FormData;
      param.append("token", this.$store.state.user.token)
      this.$axios.post("/bc208/api/tasks", param).then(res => {
        //this.tasks = Object.assign({},res.data.result)
        this.tasks = res.data.result
        this.refresh = Math.random(); //强制触发vue的diff重新渲染
      })
      this.$axios.post("/bc208/api/finished", param).then(res => {
        this.finishedTasks = res.data.result
        this.finishedTasksCount = this.finishedTasks.length
      })
    },
    loadUnfinishedTasks() {
      let param = new FormData;
      param.append("token", this.$store.state.user.token)
      this.$axios.post("/bc208/api/tasks", param).then(res => {
        this.tasks = res.data.result
      })

    },
    changeTask(task_id,id){
      this.$refs.taskInput[id].style.display = "block";
      this.$refs.taskContent[id].style.display = "none";
      this.$refs.task_input[id].focus();
      this.taskText = this.$refs.taskContent[id].innerText;
    },
    submitChange(task_id,id){
      let param = new FormData;
      param.append("token", this.$store.state.user.token);
      param.append("task_id", task_id);
      param.append("task_content", this.taskText);
      // console.log(param);
      this.$axios.post("/bc208/api/update", param).then(res=>{
        console.log(res);
        if(res.data.msg==="success") {
          this.$refs.taskInput[id].style.display = "none";
          this.$refs.taskContent[id].style.display = "block";
          this.$refs.taskContent[id].innerText = this.taskText;
        } else {
          console.log(res.data.msg);
        }
      }).catch(err=>{
        console.log(err);})
    },
    deleteTask(task_id) {
      this.$confirm('是否确认删除该任务?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let param = new FormData;
        param.append("token", this.$store.state.user.token);
        param.append("task_id", task_id);
        this.$axios.post("/bc208/api/delete", param).then(res => {
          this.loadAllTasks();//删除了再加载
          this.$message({
            type: 'success',
            message: '删除成功'
          });
        });
      }).catch(() => {
        this.$message({
          type: 'success',
          message: '已取消删除'
        });
      });
    },
    handleScroll() {
      let scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
      if (this.$route.path !== '/tasks' || this.boxCardsWidth === 0 || this.$refs.boxCards === undefined || this.$refs.boxCards.length === 0) return;
      let length = this.tasks.length
      if (scrollTop <= 10) {
        for (let i = 0; i < length; i++) {
          this.$refs.boxCards[i].$el.style.width = this.boxCardsWidth + "px"
          this.$refs.boxCards[i].$el.style.opacity = "100%"
        }
        return
      }
      let sum = 0
      let index = 1;
      let opacity = 0;
      for (let i = 0; i < length; i++) {
        let tempHeight = this.$refs.boxCards[i].$el.offsetHeight
        sum += tempHeight
        if (scrollTop <= sum && scrollTop >= sum - tempHeight) {
          opacity = (sum - scrollTop) + 10 > 100 ? 100 : (sum - scrollTop) + 10
          index = i
          break
        }
      }
      /* console.log("opacity:"+opacity)
       console.log("index:"+index)*/
      let currentWidth = this.$refs.boxCards[index].$el.clientWidth;
      /*console.log(currentWidth + " <--> "+ this.boxCardsWidth)*/
      this.$refs.boxCards[index].$el.style.opacity = opacity + "%"
      if (currentWidth === this.boxCardsWidth) {
        let out = currentWidth - 15
        this.$refs.boxCards[index].$el.style.width = out + "px"
        if (index >= 1) { //宽度调正回来
          for (let i = 0; i < index; i++) {
            this.$refs.boxCards[i].$el.style.width = this.boxCardsWidth + "px"
            this.$refs.boxCards[i].$el.style.opacity = "100%"
          }
        }
        for (let i = index + 1; i < length; i++) {
          this.$refs.boxCards[i].$el.style.width = this.boxCardsWidth + "px"
          this.$refs.boxCards[i].$el.style.opacity = "100%"
        }
      }

      /*console.log(this.$refs.boxCards['0'].$el.offsetHeight)
      console.log(this.$refs.boxCards['1'].$el.offsetHeight)
      console.log(this.$refs.boxCards['2'].$el.offsetHeight)*/
    },
    loadFinishedTasks(evt) { // 改变已完成那个button的箭头方向
      /*console.log(this.$refs.finishedButtonIcon.removeAttribute('class'))
      this.$refs.finishedButtonIcon.class = "el-icon-arrow-down";*/
      if (this.$refs.finishedButtonIcon.className === "el-icon-arrow-right") { //显示已完成的任务
        this.$refs.finishedButtonIcon.setAttribute('class', "el-icon-arrow-down")
        this.showFinished = true
      } else { //不显示已完成的任务
        this.$refs.finishedButtonIcon.setAttribute('class', "el-icon-arrow-right")
        this.showFinished = false
      }

      let target = evt.target;
      if (target.nodeName == "SPAN") {
        target = evt.target.parentNode;
      }
      target.blur();
    },
    addTaskButtonEvt() {
      this.showAddTask = !this.showAddTask;
    }
  }
}
</script>

<style>
.myClass input.el-input__inner {
  border-radius:15px;
}
</style>
<style scoped>
.tasks-container {
  max-width: 980px;
  margin: 0 auto;
  /*border: #409EFF 2px solid;*/
  padding-top: 20px;
  padding-bottom: 30px;
}

.box-card {
  margin: 0 auto;
  max-width: 780px;
  padding: 0 0;
}

.add-task-box {
  margin: 0 auto;
  max-width: 780px;
  padding: 0 0;
}
.button-box {
  margin: 0 auto;
  max-width: 780px;
  padding: 0 0;
}

.el-icon-check {
  font-weight: bolder;
}

.task-item {
  /*border: yellow 2px solid;*/
  /*font-size: 14px;
  overflow: hidden;*/
  display: grid;
  grid-template: auto 1fr auto / auto 1fr auto
}

.task-input>>>.el-input__inner{
  border-radius: 30px;    /*输入框圆角值*/
}

.changeSubmitButton{

}

.finishButton-box {
 /* border: red solid 2px;*/
  grid-column: 1 / 2;
}

.finishButton {
  /*float: left;*/
  /*margin-right: 20px;*/
}

.task-content { /*不应该为float*/
  /*float: left;*/
  /*max-width: 600px;*/
  /*width: 60%;*/
  margin-top: 3px;
  /*border: black 2px solid;*/
  grid-column: 2 / 3;
  padding-left: 5px;
  padding-right: 5px;
  word-break:break-all;
}
.task-input {
  float: left;
  width: 85%;
}

.moreButton-box {
  /*float: right;*/
/*  margin-right: 10px;*/
  grid-column: 3 / 4;
  /*border: burlywood solid 2px;*/
}

.finishedButton {
  margin-top: 20px;
  margin-bottom: 10px;
}
</style>
