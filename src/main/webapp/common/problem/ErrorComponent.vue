<template>
    <div>
        <h3 v-if="errorType === 'accessDenied'">You cannot access this page</h3>
        <h3 v-else-if="errorType === 'notFound'">The page you tried to access does not exist</h3>
        <h3 v-else-if="errorType === 'unknown'">Something unexpected happened</h3>

        <p>{{reason}}</p>
    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator';

    @Component
    export default class AccessDeniedComponent extends Vue {
        @Prop()
        errorType: ErrorType = 'unknown';

        @Prop()
        reason: string = 'No details specified.';
    }

    export type ErrorType = 'accessDenied' | 'notFound' | 'unknown';
</script>
