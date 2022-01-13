<template>
  <div class = "submit-form mt-3 mx-auto">
    <p class="headline">회원등록</p>
    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field
          v-model="member.name"
          :rules="[(v) => !!v || '이름은 필수입니다.']"
          label="이름"
          required
          ></v-text-field>
        <v-text-field
            v-model="member.address.city"
            :rules="[(v) => !!v || '도시명은 필수입니다.']"
            label="도시명"
            required></v-text-field>
        <v-text-field
            v-model="member.address.street"
            :rules="[(v) => !!v || '거리명은 필수입니다.']"
            label="거리명"
            required></v-text-field>
        <v-text-field
            v-model="member.address.zipcode"
            :rules="[(v) => !!v || '우편번호는 필수입니다.']"
            label="우편번호"
            required></v-text-field>
      </v-form>
      <v-btn color="success" class="mt-3" @click="saveMember">등록</v-btn>
    </div>
    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>회원가입에 성공했습니다.</v-card-title>
        <v-card-subtitle>Click the button to add new member !</v-card-subtitle>
        <v-card-actions>
          <v-btn color="primary" @click="newMember">추가</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>

<script>
import MemberDataService from "../services/MemberDataService";

export default {
  name: "add-member",
  data() {
    return {
      member: {
        id: null,
        name: "",
        address: {
          city: "",
          street: "",
          zipcode: ""
        }
      },
      submitted: false
    };
  },
  methods: {
    saveMember() {
      const validate = this.$refs.form.validate();
      if (validate) {
        MemberDataService.join(this.member)
            .then(response => {
              this.member.id = response.data.id;
              console.log(response.data);
              this.submitted = true;
            })
            .catch(e => {
              console.log(e);
            })
      }
    },
    newMember() {
      this.member = {};
      this.member.address = {};
      this.submitted = false;
    }
  }
};
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>