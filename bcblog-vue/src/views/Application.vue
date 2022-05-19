<template>
    <div>
        <Header class="header"></Header>
        <el-divider></el-divider>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item  label="姓名" prop="name" >
            <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
            <el-form-item label="校区" prop="region">
                <el-select v-model="ruleForm.region" placeholder="请选择西电校区">
                    <el-option label="南校区" value="south"></el-option>
                    <el-option label="北校区" value="north"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="年级" prop="age">
                <el-select v-model="ruleForm.age" placeholder="请选择入学年份">
                    <el-option label="17" value="17"></el-option>
                    <el-option label="18" value="18"></el-option>
                    <el-option label="19" value="19"></el-option>
                    <el-option label="20" value="20"></el-option>
                    <el-option label="21" value="21"></el-option>
                    <el-option label="22" value="22"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item  label="邮箱" prop="email" >
                <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item  label="电话" prop="number" >
                <el-input v-model="ruleForm.number"></el-input>
            </el-form-item>
            <el-form-item  label="QQ" prop="qq" >
                <el-input v-model="ruleForm.qq"></el-input>
            </el-form-item>


            <el-form-item label="兴趣方向" prop="type">
                <el-checkbox-group v-model="ruleForm.type">
                    <el-checkbox label="前后端开发" name="type"></el-checkbox>
                    <el-checkbox label="文案/PPT/美工" name="type"></el-checkbox>
                    <el-checkbox label="区块链安全" name="type"></el-checkbox>
                    <el-checkbox label="区块链底层链结构与共识算法" name="type"></el-checkbox>
                    <el-checkbox label="区块链与隐私计算" name="type"></el-checkbox>
                    <el-checkbox label="Defi/Game-Fi/Dao/NFT等区块链项目" name="type"></el-checkbox>
                    <el-checkbox label="区块链前沿发展研究" name="type"></el-checkbox>
                    <el-checkbox label="区块链内金融模型分析" name="type"></el-checkbox>
                    <el-checkbox label="其他区块链或开发相关方向" name="type"></el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="信息获取渠道" prop="resource">
                <el-radio-group v-model="ruleForm.resource">
                    <el-radio label="好友推荐"></el-radio>
                    <el-radio label="线上获取"></el-radio>
                    <el-radio label="其他渠道"></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="留言" prop="desc">
                <el-input type="textarea" v-model="ruleForm.desc"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
</div>
</template>

<script>
    import Header from "../components/Header";
    export default {
        name: "Application",
        components: {Header},
        data() {
            return {
                ruleForm: {
                    name: '',
                    region: '',
                    age:'',
                    email: '',
                    qq: '',
                    number: '',
                    type: [],
                    desc: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                        { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                    ],
                    region: [
                        { required: true, message: '请选择校区', trigger: 'change' }
                    ],
                    age:[
                        {required: true, message: '请选择入学年份', trigger: 'change'}
                        ],
                    email:[
                        {required: true, message: '请选择邮箱', trigger: 'change'}
                    ],
                    number:[
                        {required: true, message: '请选择联系方式', trigger: 'change'}
                    ],
                    qq:[
                        {required: true, message: '请选择QQ', trigger: 'change'}
                    ],
                    type: [
                        { type: 'array', required: true, message: '请至少选择一个兴趣方向', trigger: 'change' }
                    ],
                    desc: [
                        { required: true, message: '请填写留言或进行简短的自我介绍', trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }


</script>

<style scoped>

</style>
