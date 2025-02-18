#!/bin/bash
pod=$(kubectl get pods -n=medlemskap | grep -m 1 medlemskap-mini | cut -d " " -f1 )
kubectl port-forward -n=medlemskap ${pod} 8080:7070
