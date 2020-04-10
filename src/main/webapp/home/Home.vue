<template>
    <div>
        This is your home page!

        <p v-if="currentUser">
            Your are currently authenticated as {{currentUser.firstname}} {{currentUser.lastname}} (mail: {{currentUser.mail}}).
        </p>
        <p v-else>
            You are not currently authenticated.
        </p>

    </div>
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';
    import { AuthenticationModule } from '@/authentication/AuthenticationModule';
    import { getModule } from 'vuex-module-decorators';
    import { User } from '@/authentication/User';

    @Component
    export default class HomeComponent extends Vue {
        authenticationModule: AuthenticationModule = getModule(AuthenticationModule);

        get currentUser(): User | null {
            return this.authenticationModule.currentUser;
        }
    }
</script>
