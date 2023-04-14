<template>
  <div style="width: 100vw ; height: 100vh; overflow: hidden; display: flex">
    <div style="flex: 1;background-color: dodgerblue">
      <el-image style="width: 100%; height: 100%" fit="cover"
                src="https://img1.baidu.com/it/u=4097856652,4033702227&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"/>
    </div>

    <div class="welcome-title">
      <div style="font-size: 30px; font-weight: bold ">欢迎来到我们的学习平台</div>
      <div style="margin-top: 10px">在这里你可以学习如何搭建网站</div>
    </div>

    <div style="width: 500px; background-color: white">

      <router-view v-slot="{Component}">
      <transition name="el-fade-in-linear">
        <component :is="Component" />
      </transition>
      </router-view>



    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title{
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>