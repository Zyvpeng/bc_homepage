<template>
  <div>
    <Header></Header>
    <br/><br/><br/><br/>
    <el-row :gutter="10">
      <el-col :xs="{span:21,offset:0}" :sm="{span:16,offset:4}" :md="{span:12,offset:6}">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="邮箱" prop="email" >
            <el-input type="text" v-model="ruleForm.email" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import Header from "../components/Header";

export default {
  name: "Register",
  components: {Header},
  data() {

      const validatePass = (rule, value, callback) => {
      const PASSOWRD_REG_WEEK = /^[0-9]{6,8}$|^[A-Z]{6,8}$|^[a-z]{6,8}$|^[\W_!@#$%^&*`~()-+=]{6,8}$/;
      // eslint-disable-next-line no-control-regex
      const PASSOWRD_REG_MIDDLE = /^(?!\d+$)(?![a-zA-Z]+$)[\da-zA-Z]{8,10}$|^(?!\d+$)(?![\x00-\xff]+$)[\d\x00-\xff]{8,10}$|^(?!a-zA-Z+$)(?![\x00-\xff]+$)[a-zA-Z\x00-\xff]{8,10}$/;
      const PASSOWRD_REG_POWER = /^(?=.*[a-zA-Z])(?=.*[\W_!@#$%^&*`~()-+=])(?=.*\d)[^]{10,16}$|^(?=.*[a-zA-Z])(?=.*[\W_!@#$%^&*`~()-+=])[^]{10,16}$|^(?=.*\d)(?=.*[\W_!@#$%^&*`~()-+=])[^]{10,16}$|^(?=.*[a-zA-Z])(?=.*\d)[^]{10,16}$/;

      if (this.activeName === 'first') {
        if (value.length < 6 || value.length > 16) {
          this.$refs.tip.style.display = 'none'
          callback(new Error('密码位数为6位 ~ 16位'));
        } else if (value === '') {
          callback(new Error('请输入新密码'));
        } else if (PASSOWRD_REG_WEEK.test(value)) {
          this.$refs.level.innerText = '弱'
          this.$refs.tip.style.display = 'block'
          callback();
        } else if (PASSOWRD_REG_MIDDLE.test(value)) {
          this.$refs.level.innerText = '中'
          this.$refs.tip.style.display = 'block'
          callback();
        } else if (PASSOWRD_REG_POWER.test(value)) {
          this.$refs.level.innerText = '强'
          this.$refs.tip.style.display = 'block'
          callback();
        }
      } else {
        if (value.length < 6 || value.length > 16) {
          this.$refs.tip2.style.display = 'none'
          callback(new Error('密码位数为6位 ~ 16位'));
        } else if (value === '') {
          callback(new Error('请输入新密码'));
        } else if (PASSOWRD_REG_WEEK.test(value)) {
          this.$refs.level2.innerText = '弱'
          this.$refs.tip2.style.display = 'block'
          callback();
        } else if (PASSOWRD_REG_MIDDLE.test(value)) {
          this.$refs.level2.innerText = '中'
          this.$refs.tip2.style.display = 'block'
          callback();
        } else if (PASSOWRD_REG_POWER.test(value)) {
          this.$refs.level2.innerText = '强'
          this.$refs.tip2.style.display = 'block'
          callback();
        }
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validataEmail = (rule, value, callback) => {
      if (value === '' ) {
        callback( new Error('请输入邮箱'));
      }
      var reg = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
      if( !reg.test(value) ) { callback( new Error("请输入正确的邮箱")); }
      else { callback(); }
    };
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        //6-20位包含字符、数字和特殊字符
        var ls = 0;
        if (this.ruleForm.password !== '') {
          if(this.ruleForm.password.match(/([a-z])+/)){
            ls++;
          }
          if(this.ruleForm.password.match(/([0-9])+/)){
            ls++;
          }
          if(this.ruleForm.password.match(/([A-Z])+/)){
            ls++;
          }
          if(this.ruleForm.password.match(/([\W])+/) && !this.ruleForm.password.match(/(![\u4E00-\u9FA5])+/)){                            ls++;
          }
          if(this.ruleForm.password.length<6 || this.ruleForm.password.length>20 ){
            callback(new Error('要求6-20位字符'));
            ls=0;
          }
          if(this.ruleForm.password.match(/([\u4E00-\u9FA5])+/)){
            callback(new Error('不能包含中文字符'));
            ls=0;
          }
          switch (ls) {
            case 0: this.passwordPercent = 0;callback(new Error('数字、小写字母、大写字母以及特殊字符中四选三'));break;
            case 1: this.passwordPercent = 33;callback(new Error('数字、小写字母、大写字母以及特殊字符中四选三'));break;
            case 2: this.passwordPercent = 66;callback(new Error('数字、小写字母   、大写字母以及特殊字符中四选三'));break;
            case 3:
            case 4: this.passwordPercent = 100;break;
            default: this.passwordPercent = 0;break;
          }
        }
        callback();
      }
    };
    const validateConfirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.password !== this.ruleForm.checkPass ) {
          callback(new Error('两次输入的密码不一致'));
          // this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };

    return {
      ruleForm: {
      },
      passwordPercent:0,
      formLabelWidth: '120px',
      rules: {
        pass: [
          {required: true, validator: validatePassword, trigger: ['blur', 'change']}
        ],
        checkPass: [
          {required: true, validator: validatePass2, trigger: 'blur'}
        ],
        email:[
          {required: true, validator: validataEmail, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/bc208/api/register', this.ruleForm).then(res=>{
            console.log(res.data);
            if(res.data.msg !== "success"){
              alert(res.data.msg);
            }else{
              alert("注册成功，即将返回主页");
              this.$store.state.isLogin=true;
              this.$store.state.user.email = this.ruleForm.email;
              this.$router.push({name:'Tasks'});
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName)
    {
      this.ruleForm={};
    }
  }

}
</script>


<style scoped>
.parent {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-template-rows: repeat(9, 1fr);
  grid-column-gap: 0px;
  grid-row-gap: 0px;
}

.body { grid-area: 3 / 3 / 7 / 6; }
.head { grid-area: 1 / 1 / 2 / 8; }


</style>
