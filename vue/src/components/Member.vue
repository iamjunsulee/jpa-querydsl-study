<template>
  <div v-if="currentMember" class="edit-form py-3">
    <p class="headline">회원수정</p>
    <v-form ref="form" lazy-validation>
      <v-text-field
          v-model="currentMember.name"
          :rules="[(v) => !!v || '이름은 필수입니다.']"
          label="이름"
          required></v-text-field>
      <v-text-field
          v-model="currentMember.address.city"
          :rules="[(v) => !!v || '도시명은 필수입니다.']"
          label="도시명"
          required></v-text-field>
      <v-text-field
          v-model="currentMember.address.street"
          :rules="[(v) => !!v || '거리명은 필수입니다.']"
          label="거리명"
          required></v-text-field>
      <v-text-field
          v-model="currentMember.address.zipcode"
          :rules="[(v) => !!v || '우편번호는 필수입니다.']"
          label="우편번호"
          required></v-text-field>
      <v-btn small color="primary" class="mr-2" @click="updateMember">
        수정
      </v-btn>
      <v-btn color="error" small @click="deleteMember">
        삭제
      </v-btn>
    </v-form>
  </div>
  <div v-else>
    <p>회원을 조회한 후, 수정할 회원을 선택하세요.</p>
  </div>
</template>
<script>
import MemberDataService from "@/services/MemberDataService";

export default {
  name: "member",
  data() {
    return {
      currentMember: null,
      message: ""
    };
  },
  methods: {
    findMember(id) {
      MemberDataService.findById(id)
      .then(response => {
        this.currentMember = response.data.members;
        console.log(response.data.members);
      })
      .catch(e => {
        console.log(e);
      })
    },
    updateMember() {
      const validate = this.$refs.form.validate();
      if (validate) {
        MemberDataService.updateMember(this.currentMember.id, this.currentMember)
            .then(() => {
              this.$router.push({name: "members"});
            })
            .catch(e => {
              console.log(e);
            })
      }
    },
    deleteMember() {
      MemberDataService.deleteMember(this.currentMember.id)
          .then(() => {
            this.$router.push({name: "members"});
          })
          .catch(e => {
            console.log(e);
          })
    }
  },
  mounted() {
    this.message = "";
    this.findMember(this.$route.params.id);
  }
}
</script>
<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>