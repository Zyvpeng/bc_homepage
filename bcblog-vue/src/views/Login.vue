<template>
  <div>
    <Header></Header>
    <br/><br/><br/><br/>
    <el-row :gutter="10">
      <el-col :xs="{span:21,offset:0}" :sm="{span:16,offset:4}" :md="{span:12,offset:6}">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="ruleForm.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>

          <el-form-item>
            <span style="font-size: 13px">首次使用？</span>
            <el-button type="text" @click="register">点我注册</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import Header from "../components/Header";

export default {
  name: "Login",
  components: {Header},
  data() {

    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
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
    }
    return {
      ruleForm: {
        email: '',
        password: ''
      },
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
        email: [
          {validator: validataEmail, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm);
          this.$axios.post("/bc208/api/login", this.ruleForm).then(res=> {
            if (res.data.msg != "success") {
              alert(res.data.msg);
            } else {
              this.$router.push({name: "Tasks"});
              this.$store.state.isLogin = true;
              this.$store.state.user.email = this.ruleForm.email;
              this.$store.commit("SET_TOKEN", res.data.result.userToken );
              localStorage.setItem("userToken", res.data.result.userToken);
              localStorage.setItem("email", this.ruleForm.email);
            }
          })
          console.log(this.ruleForm);
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.ruleForm = {};
    },
    register() {
      this.$router.push({name: "Register"})
    }
  }
}
</script>

<style scoped>

</style>
