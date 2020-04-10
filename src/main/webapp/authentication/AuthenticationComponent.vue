<template>
    <div>
        <form @submit.prevent="authenticate">
            <input
                autofocus
                placeholder="Mail"
                name="mail"
                type="text"
                v-model="mail"
                :disabled="isLoading()"
            /><br/>
            <input
                placeholder="Password"
                name="password"
                type="password"
                v-model="password"
                :disabled="isLoading()"
            /><br/>
            <label for="rememberMe">Remember me</label>
            <input
                type="checkbox"
                id="rememberMe"
                name="rememberMe"
                v-model="rememberMe"
                :disabled="isLoading()"
            /><br/>
            <button type="submit">Connect</button>
        </form>

        <p>status: {{this.status}}</p>
    </div>
</template>

<script lang="ts">
    import { Component, Vue, Watch } from 'vue-property-decorator';
    import { AuthenticationModule, AuthenticationStatus } from '@/authentication/AuthenticationModule';
    import { getModule } from 'vuex-module-decorators';
    import { redirectToHome } from '@/common/router/Routes';

    @Component
    export default class AuthenticationComponent extends Vue {
        authenticationModule: AuthenticationModule = getModule(AuthenticationModule);

        mail = '';
        password = '';
        rememberMe = false;

        authenticate() {
            this.authenticationModule.authenticate({
                username: this.mail,
                password: this.password,
                rememberMe: this.rememberMe
            });
        }

        isLoading() {
            return this.status === 'pending';
        }

        get currentUser() {
            return this.authenticationModule.currentUser;
        }

        get status() {
            return this.authenticationModule.authenticationStatus;
        }

        @Watch('status')
        authenticationStatusWatcher(newValue: AuthenticationStatus) {
            this.redirectToHommeIfRequired(newValue);
        }

        private redirectToHommeIfRequired(status: AuthenticationStatus) {
            if (status === 'authenticated') {
                redirectToHome();
            }
        }
    }
</script>
