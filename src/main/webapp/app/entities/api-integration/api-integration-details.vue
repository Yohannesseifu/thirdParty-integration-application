<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apiIntegration">
        <h2 class="jh-entity-heading" data-cy="apiIntegrationDetailsHeading"><span>Api Integration</span> {{ apiIntegration.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.name }}</span>
          </dd>
          <dt>
            <span>Url</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.url }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.type }}</span>
          </dd>
          <dt>
            <span>Auth</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.auth }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.description }}</span>
          </dd>
          <dt>
            <span>Version</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.version }}</span>
          </dd>
          <dt>
            <span>Timeout</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.timeout }}</span>
          </dd>
          <dt>
            <span>Retry Retries</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.retryRetries }}</span>
          </dd>
          <dt>
            <span>Retry Delay</span>
          </dt>
          <dd>
            <span>{{ apiIntegration.retryDelay }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="apiIntegration.id"
          :to="{ name: 'ApiIntegrationEdit', params: { apiIntegrationId: apiIntegration.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./api-integration-details.component.ts"></script>
