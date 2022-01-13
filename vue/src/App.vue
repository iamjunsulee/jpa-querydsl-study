<template>
  <v-app>
    <v-app-bar app dark>
      <v-btn to="/join" text>회원가입</v-btn>
      <v-btn to="/members" text>회원조회</v-btn>
      <v-btn to="/addItem" text>상품등록</v-btn>
      <v-btn to="/items" text>상품조회</v-btn>
      <v-btn to="/order" text>주문등록</v-btn>
      <v-btn to="/orders" text>주문조회</v-btn>
    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App'
};
</script>
